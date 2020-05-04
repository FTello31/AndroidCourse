package com.example.fernando.seccion_03_recyclerview_cursoandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fernando.seccion_03_recyclerview_cursoandroid.R;
import com.example.fernando.seccion_03_recyclerview_cursoandroid.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Fernando on 23/08/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    //https://developer.android.com/training/material/lists-cards.html?hl=es-419
    // http://square.github.io/picasso/

    private List<Movie> movies; //lista de peliculas
    private int layout;//layout que queremos renderizar, con el que queremos inflar la vista
    private OnItemClickListener itemClickListener;// declaramos una interface al final

    private Context context;


    public MyAdapter(List<Movie> movies, int layout, OnItemClickListener itemClickListener) {
        this.movies = movies;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
    }

    // nos obliga a implementar el patron viewholder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflamos el layout y se lo pasamos al constructor del ViewHolder, donde manejaremos
        // toda la lógica como extraer los datos, referencias...
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        //lo pasamos directamente al constructor del viewholder
        context = parent.getContext();
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    //Es el volcado de datos, llama al "bind" que hemos creado en el viewholder, este metodo se llama cuando se crea el recycler
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //se le llama tantas veces ocmo elementos hay y llega al holder
        // Llamamos al método Bind del ViewHolder pasándole objeto y listener
        holder.bind(movies.get(position), itemClickListener);
        //se le pasa el objeto actual y el itemclicklistener , se le dice en el bind como actuar
    }


    //el numero de items que vamos a tener (el tamano de nuestra lista)
    @Override
    public int getItemCount() {
        return movies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        // Declarar los views del recycler_view_item
        public TextView textViewName;
        public ImageView imageViewPoster;

        // al viewholder le pasamos directamente el View
        public ViewHolder(View itemView) {
            // Recibe la View completa. La pasa al constructor padre y enlazamos referencias UI
            // con nuestras propiedades ViewHolder declaradas justo arriba.
            super(itemView);
            //instanciamos los views del recycler_view_item
            textViewName = (TextView) itemView.findViewById(R.id.textViewTittle);
            imageViewPoster = (ImageView) itemView.findViewById(R.id.imageViewPoster);

        }

        //metodo para hacer el volcado de datos
        public void bind(final Movie movie, final OnItemClickListener listener) {
            //procesamos los datos a renderizar
            textViewName.setText(movie.getName());
            Picasso.with(context).load(movie.getPoster()).fit().into(imageViewPoster);
            //imageViewPoster.setImageResource(movie.getPoster());

            //itemView instancia para la vista entera
            // Definimos que por cada elemento de nuestro recycler view, tenemos un click listener
            // que se comporta de la siguiente manera...
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //aqui decimos como queremos que se comporte nuestro recyclerView
                    listener.onItemClick(movie, getAdapterPosition());
                }
            });
        }
    }

    // creamos nuestro propio itemClickListener para el recyclerView
    // Declaramos nuestra interfaz con el/los método/s a implementar
    public interface OnItemClickListener {
        //tiene un metodo( puedo ponerle cualquier nombre)
        void onItemClick(Movie movie, int position);
    }

}
