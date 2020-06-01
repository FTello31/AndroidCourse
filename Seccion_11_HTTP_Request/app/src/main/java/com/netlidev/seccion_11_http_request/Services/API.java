package com.netlidev.seccion_11_http_request.Services;

import com.google.gson.GsonBuilder;
import com.netlidev.seccion_11_http_request.Beans.City;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class API {

    private static final String APPBASEURL = "https://api.github.com/";
    private static Retrofit retrofit = null;
    public static final String APPKEY = "APIKEY";

    public static Retrofit getAPI() {
        if (retrofit == null) {
            GsonBuilder builder = new GsonBuilder();
            builder.registerTypeAdapter(City.class, new MyDeserializer());

//            retrofit = new Retrofit.Builder()
//                    .baseUrl(APPBASEURL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(APPBASEURL)
                    .addConverterFactory(GsonConverterFactory.create(builder.create()))
                    .build();
        }
        return retrofit;
    }

}
