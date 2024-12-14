package com.mehmetkaradana.weatherapp.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.mehmetkaradana.weatherapp.ui.theme.WeatherAppTheme
import com.mehmetkaradana.weatherapp.viewModel.WeatherViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // val weatherViewModel = ViewModelProvider(this)[WeatherViewModel::class.java]
      //  val weatherViewModel : WeatherViewModel by viewModels()
        val weatherViewModel : WeatherViewModel by viewModels<WeatherViewModel>()

       // enableEdgeToEdge()
        setContent {
            WeatherAppTheme {
               Surface(modifier = Modifier.fillMaxSize(),
                   color = MaterialTheme.colorScheme.background) {
                 WeatherPage(viewModel = weatherViewModel)
               }
            }
        }
    }
}


