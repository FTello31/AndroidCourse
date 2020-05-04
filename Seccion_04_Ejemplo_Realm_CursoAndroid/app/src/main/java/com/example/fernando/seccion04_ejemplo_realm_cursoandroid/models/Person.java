package com.example.fernando.seccion04_ejemplo_realm_cursoandroid.models;

import android.app.Application;

import com.example.fernando.seccion04_ejemplo_realm_cursoandroid.application.MyApplication;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Person extends RealmObject {

    @PrimaryKey
    private int Id;
    private String Name;
    private RealmList<Dog> Dogs;

    public Person() {} // Only for Realm

    public Person(String name) {
        Id = MyApplication.PersonID.incrementAndGet();
        //Id =  (int)realm().where(Person.class).max("id") + 1);
        Name = name;
        Dogs = new RealmList<Dog>();
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

    public RealmList<Dog> getDogs() {
        return Dogs;
    }

}
