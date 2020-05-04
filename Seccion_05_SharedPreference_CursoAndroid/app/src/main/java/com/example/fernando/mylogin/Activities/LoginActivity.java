package com.example.fernando.mylogin.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.example.fernando.mylogin.R;
import com.example.fernando.mylogin.Utils.Util;

public class LoginActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private EditText editTextEmail;
    private EditText editTextPass;
    private Switch switchRemember;
    private Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bindUI();

        //leer las preferencias guardadas
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
        setPreferencesIfExist();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = editTextEmail.getText().toString();
                String pass = editTextPass.getText().toString();

                if (login(email, pass)) {
                    goToMain();
                    saveSharedPreferences(email, pass);
                }
            }
        });

    }

    private void bindUI() {
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPassword);
        switchRemember = findViewById(R.id.switchRemember);
        btnLogin = findViewById(R.id.btnLogin);
    }


    private void setPreferencesIfExist() {
        String email = Util.getUserMailPref(preferences);
        String pass = Util.getUserPassPref(preferences);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pass)) {
            editTextEmail.setText(email);
            editTextPass.setText(pass);
        }

    }

    private boolean login(String email, String pass) {
        if (!isValidEmail(email)) {
            Toast.makeText(this, "Email is not valid", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isValidPass(pass)) {
            Toast.makeText(this, "Pass must be 4 or more characters", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void saveSharedPreferences(String email, String pass) {
        if (switchRemember.isChecked()) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("email", email);
            editor.putString("pass", pass);
            // si pongo editor.commit(); solo pasaria a la siguinete linea cuando
            // todo lo anterior se haya ejecutado, es decir cuando se haya guardado todo

            //si pongo solo
            editor.apply();
            // es una accion asincrona, porque se va guardando en segundo plano todo

        }

    }


    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPass(String pass) {
        return pass.length() > 4;
    }


    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


}
