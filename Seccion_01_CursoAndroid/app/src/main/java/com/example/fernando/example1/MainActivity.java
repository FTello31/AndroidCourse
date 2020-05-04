
package com.example.fernando.example1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//3era forma implements ... solo para un listener
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn;
    private final String GREETER = "Hello from the other side!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Forzar y cargar icono en el Action BAr
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_myicon);// muestra el icono en el actionBar
        // File > New > Image Asset
        btn = (Button) findViewById(R.id.buttonMain);
        btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        //Acceder al segundo activity y mandarle un string
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        // (de donde , hacia donde)
        intent.putExtra("greeter",GREETER);
        startActivity(intent);

    }


}