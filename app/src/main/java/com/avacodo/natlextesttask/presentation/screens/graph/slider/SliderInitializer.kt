package com.avacodo.natlextesttask.presentation.screens.graph.slider

import com.google.android.material.slider.RangeSlider

interface SliderInitializer<T> {
    fun initSlider(
        sliderView: RangeSlider,
        sliderData: T,
        onSliderChangeAction: (timeFrom: Long, timeTo: Long) -> Unit,
    )
}