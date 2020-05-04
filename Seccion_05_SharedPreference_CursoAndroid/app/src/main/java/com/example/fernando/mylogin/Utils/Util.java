package com.example.fernando.mylogin.Utils;

import android.content.SharedPreferences;

public class Util {

    public static String getUserMailPref(SharedPreferences preferences) {
        //traer el valor del key email, si es null devolver vacio
        return preferences.getString("email", "");
    }

    public static String getUserPassPref(SharedPreferences preferences) {
        //traer el valor del key email, si es null devolver vacio
        return preferences.getString("pass", "");
    }

}
