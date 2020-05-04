package com.example.fernando.seccion_02_cursoandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<String> names;


    public MyAdapter(Context context, int layout, List<String> names) {
        this.context = context;
        this.layout = layout;
        this.names = names;
    }

    //cuantas veces vamos a iterar sobre una coleccion que le vamos a dar, el numero que de son los numero de item que estanran en ese
    @Override
    public int getCount() {
        return this.names.size();
    }

    //obtener un item de la coleccion
    @Override
    public Object getItem(int position) {
        return this.names.get(position);
    }

    //tener el id del elemento de la coleccion
    @Override
    public long getItemId(int id) {
        return id;
    }

    //coge cada elemento o cada item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //ViewHolderPattern es para optimizar cuando se traen los objetos, primero creamos una clase static
        ViewHolder holder;
        if (convertView==null){
            //Inflamos la vista que nos ha llegado con nuestro layout personalizado
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView  = layoutInflater.inflate(this.layout, null); //le pasamos una referencia, que esta en el archivo R, devuelve un View, pero mejor para que no este atado se le pasa

            holder = new ViewHolder();
            //referenciamos el elemento a modificar y lo rellenamos
            holder.nameTextView = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(holder);
        }else{

            holder = (ViewHolder) convertView.getTag();
        }

        //traemos el valor actual, dependiente de la posicion
        String currentName = names.get(position);
        //currentName = (String)getItem(position);

        //referenciamos el elemento a modificar y lo rellenamos
        holder.nameTextView.setText(currentName);

        //devolvemos la vista inflada y modificada con nuestros datos
        return convertView;
    }

    static class ViewHolder {
        //se pone todos los elementos de la UI que queremos ver (v.findViewById(R.id.textView);)
        private TextView nameTextView;

    }
}
