package com.avacodo.natlextesttask.presentation.screens.graph

import android.os.Bundle
import android.util.Log
import android.view.View
import com.avacodo.natlextesttask.R
import com.avacodo.natlextesttask.databinding.FragmentWeatherGraphBinding
import com.avacodo.natlextesttask.domain.entity.WeatherModelDomain
import com.avacodo.natlextesttask.presentation.BaseFragment
import com.avacodo.natlextesttask.presentation.screens.weasersearching.WeatherSearchingViewModel
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import kotlin.math.roundToInt
import kotlin.random.Random


class WeatherGraphFragment : BaseFragment<FragmentWeatherGraphBinding, WeatherModelDomain>(
    FragmentWeatherGraphBinding::inflate) {

    private val isCelsiusRequired = false

    override val viewModel by viewModel<WeatherSearchingViewModel>()

    override val progressBar by lazy { binding.weatherGraphProgressBar }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val weatherDataList = mutableListOf<WeatherModelDomain>()
        val xAxisSet = mutableListOf<String>()
        val yValues = mutableListOf<Entry>()
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm")
        repeat(15) {
            val weatherData =
                WeatherModelDomain(
                    locationID = "Location_ID",
                    locationName = "Location_Name",
                    temperatureInCelsius = Random.nextDouble(5.0) + 10.0,
                    maxTempValueInCelsius = 15.0,
                    minTempValueInCelsius = 10.0,
                    weatherMeasurementTime = System.currentTimeMillis() + it * 15_200_000,
                    weatherRequestCount = 15
                )
            yValues.add(Entry((it).toFloat(), weatherData.temperatureInCelsius.toFloat()))
            xAxisSet.add(dateFormat.format(weatherData.weatherMeasurementTime))
            Log.d("@#@", "onViewCreated: ${weatherData.temperatureInCelsius}")
        }

        Log.d("@#@", "onViewCreated: ${xAxisSet}")

        val dataFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                return (((value * 10).roundToInt()) / 10f).toString().substring(0, 4)
            }
        }

        val yAxisFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return if (isCelsiusRequired){
                    "${(((value * 10).roundToInt()) / 10f)}° C"
                } else {
                    "${(((value * 10).roundToInt()) / 10f)}° F"
                }
            }
        }

        val set1 = LineDataSet(yValues, "Label 1").apply {
            valueFormatter = dataFormatter
        }
        set1.valueTextSize = 12f
        set1.lineWidth = 2f

        set1.color = requireContext().getColor(R.color.high_temperature)
        val dataSet = mutableListOf<ILineDataSet>()
        dataSet.add(set1)
        val data = LineData(dataSet)

        val xAxisFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                return xAxisSet[value.toInt()]
            }
        }

        binding.weatherGraphChartView.apply {
            setScaleEnabled(false)
            setScaleEnabled(true)
            axisRight.isEnabled = false
            xAxis.valueFormatter = xAxisFormatter
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.setDrawGridLines(false)
            axisLeft.valueFormatter = yAxisFormatter
            xAxis.granularity = 0f
            description = Description().apply {
                text = "Дата"
                textSize = 14f
            }
            xAxis.labelRotationAngle = 270f
            axisLeft.setDrawGridLines(false)
            setData(data)
        }
    }
}