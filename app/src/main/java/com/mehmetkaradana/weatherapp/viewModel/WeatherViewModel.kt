package com.mehmetkaradana.weatherapp.viewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mehmetkaradana.weatherapp.data.api.Constant
import com.mehmetkaradana.weatherapp.data.api.NetworkResponse
import com.mehmetkaradana.weatherapp.data.api.RetrofitInstance
import com.mehmetkaradana.weatherapp.data.model.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {

    private val weatherApi = RetrofitInstance.weatherApi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult
//liveData sadece read-only
//mutableLiveData     livedatanın alt sınıfı veri üzerinde değişiklik yapabiliriz dışarıdan sadece izleyebiliriz
    fun getData(city : String){
       try {
           viewModelScope.launch {
               _weatherResult.value = NetworkResponse.Loading
               //aldığım responsea göre network değerini set ediyorum
               val response = weatherApi.getWeather(Constant.apiKey,city)
               if(response.isSuccessful){
                   response.body()?.let {
                       _weatherResult.value = NetworkResponse.Succes(it)
                   }
               }else{
                   _weatherResult.value = NetworkResponse.Error("Failed to load Data")
               }
           }
       }catch (e : Exception){
           _weatherResult.value = NetworkResponse.Error("Failed to load Data")
       }

    //Log.i("Searched city :" ,city)
    }
}