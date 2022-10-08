package com.avacodo.natlextesttask.presentation.screens.graph.slider

import com.google.android.material.slider.RangeSlider

interface SliderInitializer<T> {
    fun setSliderData(
        sliderView: RangeSlider,
        sliderData: T,
        onSliderChangeAction: (timeFrom: Long, timeTo: Long) -> Unit,
    )

    fun initSlider(
        sliderView: RangeSlider,
        onSliderChangeAction: (timeFrom: Long, timeTo: Long) -> Unit,
    )
}