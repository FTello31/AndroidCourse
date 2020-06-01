package com.netlidev.seccion_12_notifications;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView textViewTitle;
    TextView textViewMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewMessage = findViewById(R.id.textViewMessage);
        setIntentValues();

    }


    private void setIntentValues() {
        if (getIntent() != null) {
            textViewTitle.setText(getIntent().getStringExtra("title"));
            textViewMessage.setText(getIntent().getStringExtra("message"));
        }
    }

}
