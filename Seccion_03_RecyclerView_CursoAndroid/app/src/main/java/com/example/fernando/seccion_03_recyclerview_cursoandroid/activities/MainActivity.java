package com.example.fernando.seccion_03_recyclerview_cursoandroid.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fernando.seccion_03_recyclerview_cursoandroid.adapters.MyAdapter;
import com.example.fernando.seccion_03_recyclerview_cursoandroid.R;
import com.example.fernando.seccion_03_recyclerview_cursoandroid.models.Movie;

import java.util.ArrayList;
import java.util.List;


/*          https://developer.android.com/training/material/lists-cards.html?hl=es-419
* 1. Se crea el recycler_view_item
* 2. En el build gradle se le agrega las dependencias  compile 'com.android.support:recyclerview-v7:25.3.+'
* 3. Se crea un adaptador "MyAdapter"
*   3.1 Extiende    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{} y se implementa sus metodos
*   3.2 se crea     public static class ViewHolder extends RecyclerView.ViewHolder y se implementa sus metodos(constructor)
*   3.3 Implementamos nuestro   public interface OnItemClickListener {void onItemClick(String name, int position);}
*   3.4 Creamos el contructor de la clase "MyAdapter"
*   3.5  Implementamos los 3 metodos del 3.1 onCreateViewHolder, onBindViewHolder(se crea un metodo "bind" ), getItemCount
* 4. activity_main.xml copiar elemento recyclerView de la pagina
* 5. MainActivity  se declaran el recyclerView, el adapter y el layoutManager
*   5.1 Necesitamos instanciar mAdapter = new MyAdapter(names, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {...}
*   5.2 Necesitamos adjuntar el adaptador mRecyclerView.setAdapter(mAdapter);
*   5.3 Necesitamos adjuntar el layoutmanager mRecyclerView.setLayoutManager(mLayoutManager);
*
*
* Ahora juntamos el CardVIew con el RecyclerView
*   Creamos la clase Movie New>Java class
*   Agregamos imagenes ClickDerecho en Drawable>show in Explorer> pegar imagenes
*   Modificamos el recycler_view_item
*   en MyAdapter declaramos instanciamos los views del recycler_view_item
*
* */

public class MainActivity extends AppCompatActivity {

    private List<Movie> movies;
    private RecyclerView mRecyclerView;
    // Puede ser declarado como 'RecyclerView.Adapter' o como nuetra clase adaptador 'MyAdapter'
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // instanciamos esa lista y la tenemos rellena
        movies = this.getAllMovies();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        //mLayoutManager = new GridLayoutManager(this,2);//renderizarlo en forma de grid
        //mLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL); //renderizarlo por ejemplo con fotos de diferente tamaño(como grid), si usamos esto no se puede usar: mRecyclerView.setHasFixedSize(true);


        // Implementamos nuestro OnItemClickListener propio, sobreescribiendo el método que nosotros
        // definimos en el adaptador, y recibiendo los parámetros que necesitamos
        //se crea una instancia de un objeto que implementa al interface "onItemClicklistener" que esta en la clase Myadapter
        mAdapter = new MyAdapter(movies, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            //y se sobreescribe el metodo de esa interface
            @Override
            public void onItemClick(Movie movie, int position) {
               // Toast.makeText(MainActivity.this, name + " - " + position, Toast.LENGTH_SHORT).show();
                removeMovie(position);

            }
        });

        // Lo usamos en caso sabemos que el layout no va a cambiar de tamano, mejorando la performance. Sabemos que el layout de nuestro item del recyclerView no va a cambiar de tamaño:
        mRecyclerView.setHasFixedSize(true); // mejora el performance

        // Añade un efecto por defecto, si le pasamos null lo desactivamos. Implementa una animacion usamos la que es por defecto
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Enlazamos el layout manager y el adapter directamente al recycler view
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);


    }

    //option menu (3 puntitos)
    //1. se crea el android Resource "menu" luego estos dos metodos
    //se crea
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    //maneja eventos de click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_name:
                this.addMovie(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private List<Movie> getAllMovies() {
        return new ArrayList<Movie>() {{
            //datos de ejemplo
            add(new Movie("Xmen Dias Del Futuro Pasado",R.drawable.xmen));
            add(new Movie("Sherlock Holmes",R.drawable.holmes));
            add(new Movie("Guardianes de la Galaxia",R.drawable.guardianes));
            add(new Movie("Starwars 7",R.drawable.starwars7));

        }};
    }

    //le pasamos una posicion en este caso 0 porque se lo agregaremos en el top
    private void addMovie(int position) {
        movies.add(position, new Movie("New image"+ (++counter),R.drawable.newmovie));
        // Notificamos de un nuevo item insertado en nuestra coleccion. Le decimos al adaptador que algo a cambiado (refrescar el adapter)
        mAdapter.notifyItemInserted(position);
        // Hacemos scroll hacia la posicion donde el nuevo elemento se aloja. Ir a la position indicada
        mLayoutManager.scrollToPosition(position);
    }

    //ahora el delete
    private void removeMovie(int position) {
        movies.remove(position);
        //Notificamos de un item borrado en nuestra coleccion
        mAdapter.notifyItemRemoved(position);
    }



}
