package com.example.fernando.seccion_03_cardview_cursoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/* CardView
*   1. importar la libreria CardView en gradle compile 'com.android.support:cardview-v7:21.0.+'
*   2. ir al xml y poner xmlns:card_view="http://schemas.android.com/apk/res-auto"
*       2.1 escribimos <android.support.v7.widget.CardView  y añadimos elementos requeridos  (width and height)
*       2.2 agregar efecto "ripple"(como de clickeo) android:clickable="true"
        android:focusable="true"
        android:foreground="?android:attr/selectableItemBackground"
*
*
*
*
* */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.textViewTittle);
        textView.setText("Hello from the Card View");

    }
}
