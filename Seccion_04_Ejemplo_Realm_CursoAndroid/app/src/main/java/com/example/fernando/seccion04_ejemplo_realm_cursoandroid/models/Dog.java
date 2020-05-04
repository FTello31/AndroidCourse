package com.example.fernando.seccion04_ejemplo_realm_cursoandroid.models;

import com.example.fernando.seccion04_ejemplo_realm_cursoandroid.application.MyApplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Dog extends RealmObject {
    @PrimaryKey
    private int Id;
    private String Name;

    public Dog() {} // Only for Realm

    public Dog(String name) {
        Id = MyApplication.DogID.incrementAndGet();
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
