package com.avacodo.natlextesttask.presentation.screens.graph.slider

import com.avacodo.natlextesttask.domain.entity.SliderDataDomain
import com.google.android.material.slider.RangeSlider
import java.text.SimpleDateFormat

private const val SLIDER_LABEL_DATE_FORMAT_PATTERN = "dd.MM.yyyy - HH:mm"
private const val START_SLIDER_VALUE = 0f
private const val SLIDER_STEP_SIZE = 1f
private const val MIN_SLIDER_SEPARATION = 1f

class WeatherGraphSliderInitializer : SliderInitializer<SliderDataDomain> {
    private val dateFormat = SimpleDateFormat(SLIDER_LABEL_DATE_FORMAT_PATTERN)
    private var requestTimeList = listOf<Long>()

    override fun setSliderData(
        sliderView: RangeSlider,
        sliderData: SliderDataDomain,
    ) {
        requestTimeList = sliderData.requestTimeList
        sliderView.apply {
            values = listOf(START_SLIDER_VALUE, sliderData.minutesRange.toFloat())
            valueFrom = values.first()
            valueTo = values.last()
            stepSize = SLIDER_STEP_SIZE
            setMinSeparationValue(MIN_SLIDER_SEPARATION)
        }
    }

    override fun initSlider(
        sliderView: RangeSlider,
        onSliderChangeAction: (
            timeFrom: Long,
            timeTo: Long,
            hintTimeFrom: String,
            hintTimeTo: String,
        ) -> Unit,
    ) {
        sliderView.setLabelFormatter { value: Float ->
            dateFormat.format(requestTimeList[value.toInt()])
        }

        sliderView.addOnChangeListener { slider, _, _ ->
            onSliderChangeAction.invoke(
                requestTimeList[slider.values.first().toInt()],
                requestTimeList[slider.values.last().toInt()],
                dateFormat.format(requestTimeList[slider.values.first().toInt()]),
                dateFormat.format(requestTimeList[slider.values.last().toInt()])
            )
        }
    }
}