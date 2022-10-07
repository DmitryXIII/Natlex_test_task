package com.avacodo.natlextesttask.domain.entity

data class GraphEntryDomain(
    val locationID: String,
    val temperature: Double,
    val weatherRequestTime: Long,
)