package com.example.fernando.seccion_02_cursoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GridActivity extends AppCompatActivity {

    //https://developer.android.com/guide/topics/ui/layout/gridview.html

    private GridView gridView;

    private List<String> names;
    private MyAdapter myAdapter;
    private int counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        gridView = (GridView) findViewById(R.id.gridView);

        //Datos a mostrar
        names = new ArrayList<String>();
        names.add("Fernando");
        names.add("Johanna");
        names.add("Gohan");
        names.add("Goku");
        names.add("Milk");
        names.add("Yamcha");

        //al hacer click en un item del gridView
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //https://developer.android.com/reference/android/widget/AdapterView.html
                Toast.makeText(GridActivity.this, "Clicked: " + names.get(position), Toast.LENGTH_LONG).show();
            }
        });


        //Enlazamos con nuestro adaptador personalizado
        myAdapter = new MyAdapter(this, R.layout.grid_item, names);
        gridView.setAdapter(myAdapter);

        //registrar un context menu, le pasamos un view, cuando hagamos un long click en el gridview se lanza enotnces le pasamos el gridview
        registerForContextMenu(gridView);
    }

    //sobreescribimos metodos, Inflamos el layout del menu de opciones, para crear ese menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    //para manejar los eventos click en el menu de opciones
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                //agregar ese nuevo elemento
                this.names.add("Added N° " + (++counter));
                //TENEMOS QUE INFORMAR AL ADAPTADOR QUE HEMOS incrementado el tamaño del array
                this.myAdapter.notifyDataSetChanged();// hace que se refresque (notifica al adaptador)
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Inflar layout del context menu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        ////trae informacion de donde se ha hecho click
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(this.names.get(info.position));

        inflater.inflate(R.menu.context_menu, menu);

    }


    //Manejar los eventos click en el context menu
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //trae informacion de donde se ha hecho click
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch (item.getItemId()){
            case R.id.delete_item:
                //Borramos item clickeado
                this.names.remove(info.position);
                //TENEMOS QUE INFORMAR AL ADAPTADOR QUE HEMOS incrementado el tamaño del array
                this.myAdapter.notifyDataSetChanged();// hace que se refresque (notifica al adaptador)
                return true;
        }

        return super.onContextItemSelected(item);
    }
}
