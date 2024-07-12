package com.droid.weathernow.repository

import com.droid.weathernow.remote.ApiService
import com.droid.weathernow.utils.Constants

class RemoteCityRepository(val api: ApiService) {
    suspend fun getCityData(q: String, limit: Int) = api.getCityList(q, limit, Constants.API_KEY)
}