package com.mehmetkaradana.weatherapp.data.api

import com.mehmetkaradana.weatherapp.data.model.WeatherModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    //https://api.weatherapi.com/v1/current.json?key=de474483a30a4f03b2e123545241312&q=London&aqi=no


    @GET("/v1/current.json")
    suspend fun getWeather(
        @Query("key") apikey : String,
        @Query("q") city : String
    ) : Response<WeatherModel>
}