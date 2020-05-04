package com.example.fernando.seccion_04_realm2.app;

import android.app.Application;

import com.example.fernando.seccion_04_realm2.models.Board;
import com.example.fernando.seccion_04_realm2.models.Note;

import java.util.concurrent.atomic.AtomicInteger;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;

    //extender Application

// se crear los id autoincremental
// se crea setUpRealmConfig
public class MyApplication  extends Application {

    //podemos escribir cosas de configuracion global para la aplicacion, sera ejecutada antes del MainActivity

    public static AtomicInteger BoardID = new AtomicInteger();
    public static AtomicInteger NoteID = new AtomicInteger();


    @Override
    public void onCreate() {

        setUpRealmConfig();
        Realm realm = Realm.getDefaultInstance();
        BoardID=getIdByTable(realm, Board.class);
        NoteID=getIdByTable(realm, Note.class);
        realm.close();

        super.onCreate();
    }

    private void setUpRealmConfig(){
        Realm.init(this);
        RealmConfiguration config= new RealmConfiguration
                .Builder()
                .build();
        Realm.setDefaultConfiguration(config);
    }



    //trabajar con clases genericas, nuestra clase generica tiene que extender de RealmObject
    private <T extends RealmObject> AtomicInteger getIdByTable(Realm realm, Class<T> anyClass){
        RealmResults<T> results = realm.where(anyClass).findAll();
        return (results.size()>0) ? new AtomicInteger(results.max("id").intValue()): new AtomicInteger();

    }


}
