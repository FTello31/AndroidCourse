package com.example.fernando.seccion_04_realm2.activities;

import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fernando.seccion_04_realm2.R;
import com.example.fernando.seccion_04_realm2.adapters.BoardAdapter;
import com.example.fernando.seccion_04_realm2.models.Board;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/*
Cada Board(pizarra) tendra una serie de notas asignadas a esa pizarra

1. Se agrega REALM https://realm.io/docs/java/latest al proyecto
2. Crear paquetes click derecho -> package
3. Se crea las clases (entidades) en Models, Board y Note (extends RealmObject)
4. Se Crea MyApplication (extender de Application) - la configuracion de Realm y el autoincremental del ID
5. Configurar la BD Realm en MyApplication (private void setUpRealmConfig)
6. Crear ListActivity (activity_board)
7. para el boton flotante ir al gradle compile 'com.android.support:design:28.+'
8. descagar el signo "plus" materialdesignicons.com
9. new image asset launcher icons | ic_add_fab_ | Image
10. Se crea el AlertDialog showAlertForCreatingBoard() - es un input a partir de el boton flotante
11. Para usar el Real transaction se e agrega al manifest android:name=".app.MyApplication" , en el on Create
12. Crear layout List_view_board_item
13. Se crea el adaptador BoardAdapter
14 ir a Board Activity para linkear el board adapter se crean variables,
15. para las notas: Crear new activity empty activity (se crea el layout activity note)
16. crear list_view_note_item (layout resourcefile)
17. crear adaptador (create new java class
18. Linkear NoteAdapter con Noteactivity, se crea un dialog_create_note
19. se implementa implements RealmChangeListener<RealmResults<Board>> para que la ista se actualize cada vez que haya un cambio
20. se creara un option menu con los diferentes eventos (onCreateOptionsMenu y onOptionsItemSelected)
21. Se crea un layout (click derecho en res new - android resource directory - seleccionar menu)
22. Click derecho new a la carpeta menu (new menu resource)
23. editar o borrar cada uno de los elemneto , con un context menu onCreateContextMenu, se crea un menu nuevo'al final en el oncreate  registerForContextMenu(listView);
24. Para editar un BOard se crea un dialog nuevo
25. se ahace lo mimso para las notas, se crea los 3 puntitos onCreateOptionsMenu luego onOptionsItemSelected
26 Despues los menu Contextuales para editar y borrar uno por uno  onCreateContextMenu  y onContextItemSelected

 */
public class BoardActivity extends AppCompatActivity implements RealmChangeListener<RealmResults<Board>>,AdapterView.OnItemClickListener {

    private Realm realm;
    private FloatingActionButton fab;

    private ListView listView;
    private BaseAdapter adapter;
    private RealmResults<Board> boards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        //DB Realm
        realm = Realm.getDefaultInstance();
        boards = realm.where(Board.class).findAll();
        boards.addChangeListener(this);


        adapter = new BoardAdapter(this,boards,R.layout.list_view_board_item);
        listView = (ListView) findViewById(R.id.listViewBoard);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        //boton flotante
        fab = (FloatingActionButton) findViewById(R.id.fabAddBoard);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertForCreatingBoard("Add new Board","Type a name for your new board");
            }
        });

        registerForContextMenu(listView);

        //dejar la BD limpia
//        realm.beginTransaction();
//        realm.deleteAll();
//        realm.commitTransaction();


    }



    //CRUD
    private void createNewBoard(String boardName) {
        realm.beginTransaction();
        Board board = new Board(boardName);
        realm.copyToRealm(board);
        realm.commitTransaction();
        // lo mismo que arriba
//        realm.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//
//            }
//        });
    }

    private void editBoard(String newName, Board board){
        realm.beginTransaction();
        board.setTitle(newName);
        realm.copyToRealmOrUpdate(board);
        realm.commitTransaction();
    }

    private void deleteBoard(Board board){
        realm.beginTransaction();
        board.deleteFromRealm();
        realm.commitTransaction();
    }
    private void deleteAllBoards(){
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        }
    //Alert Dialog - pop-up con input
    private void showAlertForCreatingBoard(String tittle, String message){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);

        //lo tenemos que inflar, creamos un layout (dialog_create_ board)
        if (tittle != null) builder.setTitle(tittle);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board,null);
        builder.setView(viewInflated);

        //referencia al editText
        final EditText input = (EditText)viewInflated.findViewById(R.id.editTextBoard);

        // configurar la accion del boton
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String boardName = input.getText().toString().trim();
                if (boardName.length()>0){
                    createNewBoard(boardName);
                }else{
                    Toast.makeText(getApplicationContext(),"The name is required to create a new Board", Toast.LENGTH_LONG).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }


    private void showAlertForEditingBoard(String tittle, String message, final Board board){
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);

        //lo tenemos que inflar, creamos un layout (dialog_create_ board)
        if (tittle != null) builder.setTitle(tittle);
        if (message != null) builder.setMessage(message);

        View viewInflated = LayoutInflater.from(this).inflate(R.layout.dialog_create_board,null);
        builder.setView(viewInflated);

        //referencia al editText
        final EditText input = (EditText)viewInflated.findViewById(R.id.editTextBoard);
        input.setText(board.getTitle());
        // configurar la accion del boton
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String boardName = input.getText().toString().trim();
                if (boardName.length()==0){
                    Toast.makeText(getApplicationContext(),"The name is required to edit the current Board", Toast.LENGTH_LONG).show();
                }else if (boardName.equals(board.getTitle())){
                    Toast.makeText(getApplicationContext(),"The name is the same as it was before", Toast.LENGTH_LONG).show();
                }
                else{
                    editBoard(boardName,board);
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

// Events

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_board_activity,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_all:
                deleteAllBoards();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(boards.get(info.position).getTitle());
        getMenuInflater().inflate(R.menu.context_menu_board_activity,menu);

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_board:
                deleteBoard(boards.get(info.position));
                return true;
            case R.id.edit_board:
                showAlertForEditingBoard("Edit Board","Change the name of the board", boards.get(info.position));
                return true;
                default:
                    return super.onContextItemSelected(item);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(BoardActivity.this,NoteActivity.class);
        intent.putExtra("id",boards.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onChange(RealmResults<Board> boards) {
        adapter.notifyDataSetChanged();
    }
}
