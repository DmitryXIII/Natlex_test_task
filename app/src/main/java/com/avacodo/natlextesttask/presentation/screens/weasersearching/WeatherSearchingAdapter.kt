package com.avacodo.natlextesttask.presentation.screens.weasersearching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherSearchingItemBinding
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import java.text.SimpleDateFormat

private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm:ss"

class WeatherSearchingAdapter : Adapter<WeatherSearchingAdapter.WeatherSearchingViewHolder>() {

    private var weatherDataList = listOf<WeatherModelDomain>()

    fun setData(mWeatherDataList: List<WeatherModelDomain>) {
        weatherDataList = mWeatherDataList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherSearchingViewHolder {
        return WeatherSearchingViewHolder(
            (FragmentWeatherSearchingItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false))
            .root)
    }

    override fun onBindViewHolder(holder: WeatherSearchingViewHolder, position: Int) {
        holder.bind(weatherDataList[position])
    }

    override fun getItemCount(): Int {
        return weatherDataList.size
    }

    inner class WeatherSearchingViewHolder(view: View) : ViewHolder(view) {
        private val dateFormat = SimpleDateFormat(DATE_FORMAT_PATTERN)
        fun bind(weatherModelDomain: WeatherModelDomain) {
            FragmentWeatherSearchingItemBinding.bind(itemView).apply {
                searchingItemTempValueTextView.text = root.context.getString(
                    R.string.weather_list_item_value_in_celsius,
                    weatherModelDomain.locationName,
                    weatherModelDomain.temperatureInCelsius)

                searchingItemDateTextView.text = dateFormat.format(weatherModelDomain.weatherMeasurementTime)
            }
        }
    }
}