package com.avacodo.natlextesttask.domain.entity

import com.github.mikephil.charting.data.Entry

data class GraphDataDomain(
    val xAxisValuesList: List<String>,
    val yAxisValuesList: List<Entry>,
)