package com.droid.weathernow.repository

import com.droid.weathernow.remote.ApiService
import com.droid.weathernow.utils.Constants

class RemoteWeatherRepository(val api: ApiService) {
    fun getCurrentWeatherData(lat: Double, long: Double, unit: String)
    = api.getCurrentWeatherData(lat, long, unit, Constants.API_KEY)
}