package com.avacodo.natlextesttask.presentation.screens.weasersearching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherSearchingItemBinding
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.presentation.weatherunits.WeatherUnitsProvider
import com.avacodo.natlextesttask.presentation.weatherunits.WeatherUnitsProviderFactory
import java.text.SimpleDateFormat

private const val DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm:ss"

class WeatherSearchingAdapter : Adapter<WeatherSearchingAdapter.WeatherSearchingViewHolder>() {

    private var weatherDataList = listOf<WeatherModelDomain>()

    private var weatherUnitsProvider = WeatherUnitsProviderFactory().initWeatherUnitsProvider(true)

    fun setData(mWeatherDataList: List<WeatherModelDomain>) {
        val diffUtilCallback = DiffUtilCallback(weatherDataList, mWeatherDataList)
        weatherDataList = mWeatherDataList
        DiffUtil.calculateDiff(diffUtilCallback).dispatchUpdatesTo(this)
    }

    fun setWeatherUnits(mIsSwitchChecked: Boolean) {
        weatherUnitsProvider =
            WeatherUnitsProviderFactory().initWeatherUnitsProvider(mIsSwitchChecked)
        notifyItemRangeChanged(0, weatherDataList.size)
    }

    private fun initWeatherValueProvider(isSwitchChecked: Boolean): WeatherUnitsProvider {
        return WeatherUnitsProviderFactory().initWeatherUnitsProvider(isSwitchChecked)
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
                searchingItemLocationNameTextView.text =
                    itemView.context.getString(R.string.weather_list_item_location_name,
                        weatherModelDomain.locationName)
                searchingItemTempValueTextView.text =
                    weatherUnitsProvider.provideWeatherValue(itemView.context, weatherModelDomain)


                searchingItemDateTextView.text =
                    dateFormat.format(weatherModelDomain.weatherMeasurementTime)
            }
        }
    }

    class DiffUtilCallback(
        private val oldList: List<WeatherModelDomain>,
        private val newList: List<WeatherModelDomain>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return areContentsTheSame(oldItemPosition, newItemPosition)
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }
}