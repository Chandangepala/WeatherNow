package com.droid.weathernow.view_models

import androidx.lifecycle.ViewModel
import com.droid.weathernow.remote.ApiClient
import com.droid.weathernow.remote.ApiService
import com.droid.weathernow.repository.RemoteCityRepository

class CityViewModel(val repository: RemoteCityRepository): ViewModel() {
    constructor() : this(RemoteCityRepository(ApiClient().getRetrofitClient().create(ApiService::class.java)))

    suspend fun loadCity(q: String, limit: Int) = repository.getCityData(q, limit)
}