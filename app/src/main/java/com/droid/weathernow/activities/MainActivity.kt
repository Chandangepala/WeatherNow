package com.droid.weathernow.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import com.droid.weathernow.R
import com.droid.weathernow.databinding.ActivityMainBinding
import com.droid.weathernow.models.CurrentResponseAPI
import com.droid.weathernow.view_models.CityViewModel
import com.droid.weathernow.view_models.WeatherViewModel
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val weatherViewModel :  WeatherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }


        binding.apply {

            var lat = intent.getDoubleExtra("lat", 0.0)
            var long = intent.getDoubleExtra("lng",0.0)
            var name = intent.getStringExtra("name")

            changeLocationCard.setOnClickListener {
                startActivity(Intent(this@MainActivity, CityActivity::class.java))
            }

            if (lat == 0.0){
                lat = 26.1158
                long = 91.7086
                name = "Guwahati"
            }
            lottieProgress.visibility = View.VISIBLE

            cityNameTxt.text = name
            weatherViewModel.loadCurrentWeatherData(lat, long, "metric").enqueue(object : Callback<CurrentResponseAPI>{
                override fun onResponse(
                    call: Call<CurrentResponseAPI>,
                    response: Response<CurrentResponseAPI>
                ) {
                    lottieProgress.visibility = View.GONE
                    if(response.isSuccessful){
                        val data = response.body()
                        data?.let {
                            cityNameTxt.text = it.name
                            tempText.text = "${it.main?.temp.toString()}°C"
                            weatherConditionTxt.text = it.weather?.get(0)?.main
                            windTxt.text = it.wind?.speed.toString() + "Kmph"
                            humidityTxt.text = it.main?.humidity.toString() + "%"
                            main.setBackgroundResource(setDynamicBackground(it.weather?.get(0)?.icon?:"-"))
                        }
                    }
                }

                override fun onFailure(call: Call<CurrentResponseAPI>, t: Throwable) {
                    Snackbar.make(main, "Error: ${t.message}", Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun setDynamicBackground(icon: String): Int{
        return when(icon.dropLast(1)){
            "01" -> R.drawable.sunny_bg
            "02", "03", "04" -> R.drawable.cloudy_bg
            "09", "10", "11" -> R.drawable.rainy_bg
            "13" -> R.drawable.snow_bg
            "50" -> R.drawable.haze_bg
            else -> 0
        }
    }
}