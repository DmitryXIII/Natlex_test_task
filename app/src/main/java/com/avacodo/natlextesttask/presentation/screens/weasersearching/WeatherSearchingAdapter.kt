package com.avacodo.natlextesttask.presentation.screens.weasersearching

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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

class WeatherSearchingAdapter(private val clickListener: OnRecyclerItemClickListener) :
    Adapter<WeatherSearchingAdapter.WeatherSearchingViewHolder>() {

    private var weatherDataList = listOf<WeatherModelDomain>()

    private var weatherUnitsProvider = WeatherUnitsProviderFactory().initWeatherUnitsProvider(true)

    fun setData(mWeatherDataList: List<WeatherModelDomain>) {
        val diffUtilCallback = DiffUtilCallback(weatherDataList, mWeatherDataList)
        weatherDataList = mWeatherDataList
        DiffUtil.calculateDiff(diffUtilCallback).dispatchUpdatesTo(this)
    }

    fun setWeatherUnits(mWeatherUnitsProvider: WeatherUnitsProvider) {
        weatherUnitsProvider = mWeatherUnitsProvider
        notifyItemRangeChanged(0, weatherDataList.size)
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
                initMainItemData(this, weatherModelDomain)
                initOptionalItemData(this, weatherModelDomain)
            }
        }

        private fun initMainItemData(
            binding: FragmentWeatherSearchingItemBinding,
            weatherModelDomain: WeatherModelDomain,
        ) {
            with(binding) {
                with(binding.root.context) {
                    if (weatherModelDomain.weatherRequestCount < 2) {
                        searchingItemOptionalGroup.isVisible = false
                    } else {
                        searchingItemOptionalGroup.isVisible = true

                        searchingItemMaxTempTextView.text =
                            weatherUnitsProvider.provideMaxTempValue(
                                this,
                                weatherModelDomain
                            )

                        searchingItemMinTempTextView.text =
                            weatherUnitsProvider.provideMinTempValue(
                                this,
                                weatherModelDomain
                            )

                        tempGraphImageView.setOnClickListener {
                            clickListener.onItemClick(weatherModelDomain.locationID)
                        }
                    }
                }
            }
        }

        private fun initOptionalItemData(
            binding: FragmentWeatherSearchingItemBinding,
            weatherModelDomain: WeatherModelDomain,
        ) {
            with(binding) {
                with(binding.root.context) {
                    searchingItemLocationNameTextView.text = this.getString(
                        R.string.weather_list_item_location_name,
                        weatherModelDomain.locationName
                    )

                    searchingItemTempValueTextView.text = weatherUnitsProvider.provideWeatherValue(
                        this,
                        weatherModelDomain
                    )

                    searchingItemDateTextView.text = dateFormat.format(
                        weatherModelDomain.weatherMeasurementTime
                    )
                }
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

fun interface OnRecyclerItemClickListener {
    fun onItemClick(locationID: String)
}