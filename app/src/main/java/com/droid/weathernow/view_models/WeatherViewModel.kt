package com.droid.weathernow.view_models

import androidx.lifecycle.ViewModel
import com.droid.weathernow.remote.ApiClient
import com.droid.weathernow.remote.ApiService
import com.droid.weathernow.repository.RemoteWeatherRepository

class WeatherViewModel(val repository: RemoteWeatherRepository): ViewModel() {

    constructor(): this(RemoteWeatherRepository(ApiClient().getRetrofitClient().create(ApiService:: class.java)))

    fun loadCurrentWeatherData(lat: Double, long: Double, unit: String) = repository.getCurrentWeatherData(lat, long, unit)
}