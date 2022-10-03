package com.avacodo.natlextesttask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avacodo.natlextesttask.data.local.entity.WeatherLocalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addWeatherData(weatherLocalEntity: WeatherLocalEntity)

    @Query("SELECT *, MAX (weatherMeasurementTime)" +
            "FROM WeatherLocalEntity " +
            "GROUP BY locationID " +
            "ORDER BY weatherMeasurementTime DESC")
    fun getWeatherData(): Flow<List<WeatherLocalEntity>>

    @Query("SELECT MAX(temperatureInCelsius) " +
            "FROM WeatherLocalEntity " +
            "WHERE locationID = :locationID")
    fun getMaxTempValueByLocationID(locationID: String): Double

    @Query("SELECT MIN(temperatureInCelsius) " +
            "FROM WeatherLocalEntity " +
            "WHERE locationID = :locationID")
    fun getMinTempValueByLocationID(locationID: String): Double

    @Query("SELECT COUNT(locationID) " +
            "FROM WeatherLocalEntity " +
            "WHERE locationID = :locationID ")
    fun getCountByLocationID(locationID: String): Int
}