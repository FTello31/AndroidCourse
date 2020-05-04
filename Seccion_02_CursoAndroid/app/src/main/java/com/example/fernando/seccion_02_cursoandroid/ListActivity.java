package com.example.fernando.seccion_02_cursoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {


    private ListView listView;
    private List<String> names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //un ListView es una lista de Views

        listView = (ListView) findViewById(R.id.listView);

        //Datos a mostrar
        names = new ArrayList<String>();
        names.add("Fernando");
        names.add("Johanna");
        names.add("Gohan");
        names.add("Goku");
        names.add("Milk");
        names.add("Yamcha");
        names.add("Fernando");
        names.add("Johanna");
        names.add("Gohan");
        names.add("Goku");
        names.add("Milk");
        names.add("Yamcha");
        names.add("Fernando");
        names.add("Johanna");
        names.add("Gohan");
        names.add("Goku");
        names.add("Milk");
        names.add("Yamcha");

        /* Otra forma de hacer los ArrayList
        List<String> names2 = new ArrayList<String>(){{
            add("Fernando");
            add("Johanna");
            add("Gohan");
        }};*/

        //Antes: Adaptador, la forma visual en que mostraremos nuestros datos
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names);

        //Antes: Enlazamos el adaptador con nuestro ListView
        //listView.setAdapter(adapter);

        //al hacer click en un item del listView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //https://developer.android.com/reference/android/widget/AdapterView.html
                Toast.makeText(ListActivity.this, "Clicked: " + names.get(position), Toast.LENGTH_LONG).show();
            }
        });


        //enlazamos con nuestro adaptador personalizado
        MyAdapter myAdapter = new MyAdapter(this, R.layout.list_item, names);
        listView.setAdapter(myAdapter);
    }
}

