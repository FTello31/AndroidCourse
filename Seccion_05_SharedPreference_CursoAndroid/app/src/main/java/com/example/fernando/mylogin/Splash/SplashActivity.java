package com.example.fernando.mylogin.Splash;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.fernando.mylogin.Activities.LoginActivity;
import com.example.fernando.mylogin.Activities.MainActivity;
import com.example.fernando.mylogin.Utils.Util;

public class SplashActivity extends AppCompatActivity {


    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);

        Intent intentLogin = new Intent(this,LoginActivity.class);
        Intent intentMain = new Intent(this,MainActivity.class);


        if (!TextUtils.isEmpty(Util.getUserMailPref(preferences)) && !TextUtils.isEmpty(Util.getUserPassPref(preferences))){
            startActivity(intentMain);
        }else{
            startActivity(intentLogin);
        }
        finish();

    }
}
