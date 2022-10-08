package com.avacodo.natlextesttask.presentation.screens.graph.slider

import com.avacodo.natlextesttask.domain.entity.WeatherGraphDataDomain
import com.google.android.material.slider.RangeSlider
import java.text.SimpleDateFormat

private const val SLIDER_LABEL_DATE_FORMAT_PATTERN = "dd.MM.yyyy - HH:mm"
private const val MILLISECONDS_IN_MINUTE = 60000
private const val START_SLIDER_VALUE = 0f
private const val SLIDER_STEP_SIZE= 1f
private const val MIN_SLIDER_SEPARATION= 1f

class WeatherGraphSliderInitializer : SliderInitializer<WeatherGraphDataDomain> {
    override fun initSlider(
        sliderView: RangeSlider,
        sliderData: WeatherGraphDataDomain,
        onSliderChangeAction: (timeFrom: Long, timeTo: Long) -> Unit,
    ) {
        val requestTimesList = mutableListOf<Long>()
        val startTime = sliderData.minRequestTime / MILLISECONDS_IN_MINUTE * MILLISECONDS_IN_MINUTE
        val endTime = sliderData.maxRequestTime / MILLISECONDS_IN_MINUTE * MILLISECONDS_IN_MINUTE
        val minutesCount = ((endTime - startTime) / MILLISECONDS_IN_MINUTE).toInt() + 1
        val dateFormat = SimpleDateFormat(SLIDER_LABEL_DATE_FORMAT_PATTERN)

        for (i in 0..minutesCount) {
            requestTimesList.add(startTime + i * MILLISECONDS_IN_MINUTE)
        }

        sliderView.apply {
            values = listOf(START_SLIDER_VALUE, minutesCount.toFloat())
            valueFrom = values.first()
            valueTo = values.last()
            stepSize = SLIDER_STEP_SIZE
            setMinSeparationValue(MIN_SLIDER_SEPARATION)

            setLabelFormatter { value: Float ->
                dateFormat.format(requestTimesList[value.toInt()])
            }

            addOnChangeListener { slider, _, _ ->
                onSliderChangeAction.invoke(
                    requestTimesList[slider.values.first().toInt()],
                    requestTimesList[slider.values.last().toInt()]
                )
            }
        }
    }
}