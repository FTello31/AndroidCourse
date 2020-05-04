package com.example.fernando.example1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private TextView textView;
    private Button buttonNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //Activar flecha ir atras
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        textView = (TextView) findViewById(R.id.textViewMain);
        buttonNext = (Button) findViewById(R.id.buttonGoSharing);

        //Tomar los datos del intent
        //Se rescata en forma de bundle, es una caja donde se almacena los datos
        Bundle bundle = getIntent().getExtras();
        if (bundle !=null && bundle.getString("greeter")!=null){
            String greeter =bundle.getString("greeter");
            Toast.makeText(SecondActivity.this,greeter,Toast.LENGTH_LONG).show();
            textView.setText(greeter);
        }else {
            Toast.makeText(SecondActivity.this,"It is empty",Toast.LENGTH_LONG).show();
        }


        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,ThirdActivity.class);
                startActivity(intent);
            }
        });
    }


}
