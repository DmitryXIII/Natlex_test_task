package com.avacodo.natlextesttask.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(
        value = ["locationID", "weatherMeasurementTime"],
        unique = true)]
)
class WeatherLocalEntity(
    @PrimaryKey(autoGenerate = true)
    val ID: Int = 0,
    val locationID: String,
    val locationName: String,
    val temperatureInCelsius: Double,
    val weatherMeasurementTime: Long,
)