package com.mehmetkaradana.weatherapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mehmetkaradana.weatherapp.data.api.NetworkResponse
import com.mehmetkaradana.weatherapp.data.model.WeatherModel
import com.mehmetkaradana.weatherapp.viewModel.WeatherViewModel

@Composable
fun WeatherPage(viewModel: WeatherViewModel) {

    var city by remember {
        mutableStateOf("")
    }

    val weatherResult = viewModel.weatherResult.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,//dikeyde ortala
            horizontalArrangement = Arrangement.SpaceEvenly//yatayda
        ){                               //Modifier.filmaxWidth(1f) no no noooo
            OutlinedTextField(modifier = Modifier.weight(1f),value =city , onValueChange ={
                city=it
            }, label = {Text(text = "Search for any location" ) })

            IconButton(onClick = {
                viewModel.getData(city)
                keyboardController?.hide()
            }) {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription ="Search for any location" )
            }
        }
        when(val result =weatherResult.value){
            is NetworkResponse.Error -> {
                Spacer(modifier = Modifier.padding(bottom = 25.dp))
                    Text(text = result.message)
            }
            NetworkResponse.Loading ->{
                Spacer(modifier = Modifier.padding(bottom = 25.dp))
                CircularProgressIndicator()}
            is NetworkResponse.Succes -> {
             //   Spacer(modifier = Modifier.padding(bottom = 25.dp))
                WeatherDetails(data = result.data)
            }
            null -> {}
        }

    }

}

@Composable
private fun WeatherDetails(data : WeatherModel){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Icon(imageVector = Icons.Default.LocationOn,
                contentDescription ="Location icon",
                modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.padding(5.dp))
            Text(text = "${data.location.name},", fontSize = 30.sp)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
        }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(text = "${data.current.temp_c}°c",
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.padding(5.dp))
       // println("https:${data.current.condition.icon}")
            AsyncImage(model = "https:${data.current.condition.icon}".replace("64x64","128x128"),
                contentDescription =data.current.condition.text ,
                modifier = Modifier.size(160.dp),
              /*  onSuccess = {
                    println("BasicContentCard: Success!")
                },
                onError = {
                    //Log.isLoggable("hata:", Log.ASSERT)
                    println(it)
                }*/)
        Text(text = data.current.condition.text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold)

Spacer(modifier = Modifier.padding(16.dp))

        Card(modifier = Modifier.padding(16.dp)) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp, top = 5.dp)) {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround){
                    cardParameter(parameter ="Humidity" , value =data.current.humidity )
                    Spacer(modifier = Modifier.padding(5.dp))
                    cardParameter(parameter ="Feels Like" , value =data.current.feelslike_c )
                 //   Spacer(modifier = Modifier.padding(5.dp))
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround){
                    cardParameter(parameter ="Wind Speed" , value =data.current.wind_kph+" "+ " kph" )
                    Spacer(modifier = Modifier.padding(5.dp))
                    cardParameter(parameter ="Partition" , value ="${data.current.precip_mm} mm" )
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    cardParameter(parameter = "Local Time", value =data.location.localtime.split(" ")[1] )
                    Spacer(modifier = Modifier.padding(5.dp))
                    cardParameter(parameter = "Local Date", value =data.location.localtime.split(" ")[0] )
                }
            }
            
        }


    }
}

@Composable
fun cardParameter(parameter: String,value: String){
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, fontWeight = FontWeight.Bold)
        Text(text = parameter)
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}