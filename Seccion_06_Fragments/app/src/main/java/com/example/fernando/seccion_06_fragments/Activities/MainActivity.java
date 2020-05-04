package com.example.fernando.seccion_06_fragments.Activities;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.fernando.seccion_06_fragments.Fragments.DataFragment;
import com.example.fernando.seccion_06_fragments.Fragments.DetailFragment;
import com.example.fernando.seccion_06_fragments.R;

public class MainActivity extends FragmentActivity implements DataFragment.DataListener {

    private boolean isMultiPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setMultiPanel();
    }

    @Override
    public void sendData(String text) {

        if (isMultiPanel) {
            DetailFragment detailFragment = (DetailFragment) getSupportFragmentManager().findFragmentById(R.id.detailFragment);
            detailFragment.renderText(text);
        } else {
            Intent intent = new Intent(this,DetailsActivity.class);
            intent.putExtra("text",text);
            startActivity(intent);
        }


    }

    private void setMultiPanel() {
        isMultiPanel = (getSupportFragmentManager().findFragmentById(R.id.detailFragment) != null);
    }
}
