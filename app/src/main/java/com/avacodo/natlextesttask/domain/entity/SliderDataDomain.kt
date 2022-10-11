package com.avacodo.natlextesttask.domain.entity

data class SliderDataDomain(
    val maxRequestTime: Long,
    val minRequestTime: Long,
    val minutesRange: Int,
    val requestTimeList: List<Long>,
)