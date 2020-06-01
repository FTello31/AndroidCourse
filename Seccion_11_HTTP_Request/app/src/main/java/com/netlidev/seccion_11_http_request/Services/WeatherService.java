package com.netlidev.seccion_11_http_request.Services;

import com.netlidev.seccion_11_http_request.Beans.City;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {

    @GET("weather")
    Call<City> getCity(@Query("q") String city,@Query("appid") String appid);
}

