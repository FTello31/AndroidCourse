package com.example.fernando.seccion_04_realm2.models;

import com.example.fernando.seccion_04_realm2.app.MyApplication;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

//1. extender de real object
public class Board extends RealmObject{

    @PrimaryKey
    private int id;
    @Required
    private String title;
    @Required
    private Date createdAt;

    //un Board tiene varios Notes
    private RealmList<Note> notes;

    public Board(){}


    public Board(String title) {
        this.id= MyApplication.BoardID.incrementAndGet();
        this.title = title;
        this.createdAt = new Date();
        this.notes = new RealmList<Note>();

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public RealmList<Note> getNotes() {
        return notes;
    }

}
