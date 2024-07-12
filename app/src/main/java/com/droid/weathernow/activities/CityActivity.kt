package com.droid.weathernow.activities

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.droid.weathernow.R
import com.droid.weathernow.adapters.CityAdapter
import com.droid.weathernow.databinding.ActivityCityBinding
import com.droid.weathernow.models.CityResponseAPI
import com.droid.weathernow.view_models.CityViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CityActivity : AppCompatActivity() {

    lateinit var binding: ActivityCityBinding
    private val cityViewModel: CityViewModel by viewModels()
    private val cityAdapter by lazy { CityAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        window.apply {
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            statusBarColor = Color.TRANSPARENT
        }

        binding.apply {
            citySelectEdt.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {

                    lifecycleScope.launch {
                        val response = cityViewModel.loadCity(s.toString(), limit = 10)
                        val data = response
                        data.let {
                            cityAdapter.differ.submitList(it)
                            recyclerViewCity.apply {
                                layoutManager = LinearLayoutManager(
                                    this@CityActivity,
                                    LinearLayoutManager.VERTICAL,
                                    false
                                )
                                adapter = cityAdapter
                            }
                        }
                    }
                    /*cityViewModel.loadCity(s.toString(), limit = 10).enqueue(object : Callback<CityResponseAPI>{
                        override fun onResponse(
                            call: Call<CityResponseAPI>,
                            response: Response<CityResponseAPI>
                        ) {
                            if(response.isSuccessful){
                                val data = response.body()
                                data?.let {
                                    for(city in data)
                                        Log.e("CityName", "${city.name}")
                                    cityAdapter.differ.submitList(it)
                                    recyclerViewCity.apply {
                                        layoutManager = LinearLayoutManager(
                                            this@CityActivity,
                                            LinearLayoutManager.VERTICAL,
                                            false
                                        )
                                        adapter = cityAdapter
                                    }
                                }
                            }
                        }

                        override fun onFailure(p0: Call<CityResponseAPI>, p1: Throwable) {

                        }

                    })
                */
                }

            })
        }
    }
}