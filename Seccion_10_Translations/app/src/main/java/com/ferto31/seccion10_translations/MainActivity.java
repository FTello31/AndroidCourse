package com.ferto31.seccion10_translations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

/*
SOlo crear otro strings.xml con el idioma que se quiere.
usar el getStrings para aceder al valor, y en los fragments usar context.getString.

 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(this, getString(R.string.welcome), Toast.LENGTH_LONG).show();
    }


}
