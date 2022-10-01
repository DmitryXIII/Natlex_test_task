package com.avacodo.natlextesttask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avacodo.natlextesttask.data.local.entity.WeatherLocalEntity

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWeatherData(weatherLocalEntity: WeatherLocalEntity)

    @Query("SELECT * FROM WeatherLocalEntity")
    fun getWeatherData(): List<WeatherLocalEntity>
}