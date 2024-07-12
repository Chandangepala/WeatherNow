package com.droid.weathernow.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.droid.weathernow.R
import com.droid.weathernow.activities.MainActivity
import com.droid.weathernow.databinding.ItemCityBinding
import com.droid.weathernow.models.CityResponseAPI

class CityAdapter : RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    lateinit var binding: ItemCityBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemCityBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val binding = ItemCityBinding.bind(holder.itemView)
        Log.e("CurrentListSize", differ.currentList.size.toString())
        //holder.itemView.findViewById<TextView>(R.id.item_city_name_txt).text = differ.currentList[position].name
        val city = differ.currentList[position]
        Log.e("AdapterCityName", city.name.toString())
        holder.cityTxt.text = city.name
        holder.cityItem.setOnClickListener {
            val intent = Intent(binding.root.context, MainActivity::class.java)
            intent.putExtra("lat", differ.currentList[position].lat)
            intent.putExtra("lng", differ.currentList[position].lon)
            intent.putExtra("name", differ.currentList[position].name)
            binding.root.context.startActivity(intent)
        }
    }

    inner class ViewHolder(binding: ItemCityBinding): RecyclerView.ViewHolder(binding.root){
        val cityItem = binding.root
        val cityTxt = binding.itemCityNameTxt
    }

    private val differCallback = object : DiffUtil.ItemCallback<CityResponseAPI.CityResponseAPIItem>(){
        override fun areItemsTheSame(
            oldItem: CityResponseAPI.CityResponseAPIItem,
            newItem: CityResponseAPI.CityResponseAPIItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CityResponseAPI.CityResponseAPIItem,
            newItem: CityResponseAPI.CityResponseAPIItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}