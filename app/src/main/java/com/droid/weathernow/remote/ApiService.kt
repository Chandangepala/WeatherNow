package com.droid.weathernow.remote

import com.droid.weathernow.models.CityResponseAPI
import com.droid.weathernow.models.CurrentResponseAPI
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/weather")
    fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") ApiKey: String
    ): Call<CurrentResponseAPI>

    @GET("geo/1.0/direct")
    suspend fun getCityList(
        @Query("q") q: String,
        @Query("limit") limit: Int,
        @Query("appid") ApiKey: String,

    ): List<CityResponseAPI.CityResponseAPIItem>
}