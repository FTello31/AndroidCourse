package com.example.fernando.seccion_04_realm2.activities;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fernando.seccion_04_realm2.R;
import com.example.fernando.seccion_04_realm2.adapters.NoteAdapter;
import com.example.fernando.seccion_04_realm2.models.Board;
import com.example.fernando.seccion_04_realm2.models.Note;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;

public class NoteActivity extends AppCompatActivity implements RealmChangeListener<Board> {

    private ListView listView;
    private FloatingActionButton fab;

    private NoteAdapter adapter;
    private RealmList<Note> notes;
    private Realm realm;

    private int boardId;
    private Board board;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);


        realm = Realm.getDefaultInstance();

        if (getIntent().getExtras() != null)
            boardId = getIntent().getExtras().getInt("id");

        board = realm.where(Board.class).equalTo("id", boardId).findFirst();
        board.addChangeListener(this);
        notes = board.getNotes();

        this.setTitle(board.getTitle());

        fab = findViewById(R.id.fabAddNote);
        listView = findViewById(R.id.listViewNote);
        adapter = new NoteAdapter(this, notes, R.layout.list_view_note_item);


        listView.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForCreatingNote("Add New Note", "Type a note for " + board.getTitle() + ".");
            }
        });

        registerForContextMenu(listView);
    }

    //CRUD
    private void createNewNote(String note) {
        realm.beginTransaction();
        Note note1 = new Note(note);
        realm.copyToRealm(note1);
        board.getNotes().add(note1);
        realm.commitTransaction();
    }

    private void editNote(String newNoteDescription, Note note){
        realm.beginTransaction();
        note.setDescription(newNoteDescription);
        realm.copyToRealmOrUpdate(note);
        realm.commitTransaction();
    }

    private void deleteNote(Note note){
        realm.beginTransaction();
        note.deleteFromRealm();
        realm.commitTransaction();
    }
    private void deleteAllNotes(){
        realm.beginTransaction();
        board.getNotes().deleteAllFromRealm();
        realm.commitTransaction();
    }




    //Alert Dialog - pop-up con input
    private void showAlertForCreatingNote(String tittle, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        if (tittle != null) builder.setTitle(tittle);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note, null);
        builder.setView(viewInflated);

        //referencia al editText
        final EditText input = (EditText) viewInflated.findViewById(R.id.editTextNewNote);

        // configurar la accion del boton
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String note = input.getText().toString().trim();
                if (note.length() > 0) {
                    createNewNote(note);
                } else {
                    Toast.makeText(getApplicationContext(), "The note can't be empty", Toast.LENGTH_LONG).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void showAlertForEditingNote(String tittle, String message, final Note note){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);

        //lo tenemos que inflar, creamos un layout (dialog_create_ board)
        if (tittle != null) builder.setTitle(tittle);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_note,null);
        builder.setView(viewInflated);

        //referencia al editText
        final EditText input = (EditText)viewInflated.findViewById(R.id.editTextNewNote);
        input.setText(note.getDescription());
        // configurar la accion del boton
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String noteName = input.getText().toString().trim();
                if (noteName.length()==0){
                    Toast.makeText(getApplicationContext(),"The text for the note is required to be edited", Toast.LENGTH_LONG).show();
                }else if (noteName.equals(board.getTitle())){
                    Toast.makeText(getApplicationContext(),"The note is the same as it was before", Toast.LENGTH_LONG).show();
                }
                else{
                    editNote(noteName,note);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

//    Events
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_note_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all_notes:
                deleteAllNotes();
                return true;
            default:
                  return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate((R.menu.context_menu_note_activity), menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()){
            case R.id.edit_note:
                showAlertForEditingNote("Edit Note","Change the description of the Note",notes.get(info.position));
                return true;
            case R.id.delete_note:
                deleteNote(notes.get(info.position));
                return true;
                default:
                    return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onChange(Board board) {
        adapter.notifyDataSetChanged();
    }
}