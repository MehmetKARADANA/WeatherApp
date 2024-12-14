package com.mehmetkaradana.weatherapp.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    //api key //de474483a30a4f03b2e123545241312
    //https://api.weatherapi.com/v1/current.json?key=de474483a30a4f03b2e123545241312&q=London&aqi=no
    private val baseUrl = "https://api.weatherapi.com"

    private fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherApi : WeatherApi = getInstance().create(WeatherApi::class.java)



}