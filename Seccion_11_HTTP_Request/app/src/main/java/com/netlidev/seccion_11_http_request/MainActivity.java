package com.netlidev.seccion_11_http_request;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.netlidev.seccion_11_http_request.Services.API;
import com.netlidev.seccion_11_http_request.Services.WeatherService;
import com.netlidev.seccion_11_http_request.Beans.City;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        WeatherService service = API.getAPI().create(WeatherService.class);

        Call<City> cityCall = service.getCity("London,UK", API.APPKEY);

        cityCall.enqueue(new Callback<City>() {
            @Override
            public void onResponse(Call<City> call, Response<City> response) {
                City city = response.body();
            }

            @Override
            public void onFailure(Call<City> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed Call "+ t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
