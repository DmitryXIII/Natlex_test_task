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

    @Query("SELECT * " +
            "FROM WeatherLocalEntity " +
            "WHERE locationID = :locationID")
    fun getLocalWeatherDataByID(locationID: String): List<WeatherLocalEntity>

    @Query("SELECT MIN(weatherMeasurementTime) " +
            "FROM WeatherLocalEntity " +
            "WHERE locationID = :locationID")
    fun getMinRequestTime(locationID: String): Long

    @Query("SELECT MAX(weatherMeasurementTime) " +
            "FROM WeatherLocalEntity " +
            "WHERE locationID = :locationID")
    fun getMaxRequestTime(locationID: String): Long

    @Query("SELECT *" +
            "FROM WeatherLocalEntity " +
            "WHERE locationID = :locationID " +
            "AND weatherMeasurementTime >= :timeFrom " +
            "AND weatherMeasurementTime <= :timeTo")
    fun getLocalWeatherDataByTimeRange(
        locationID: String,
        timeFrom: Long,
        timeTo: Long,
    ): List<WeatherLocalEntity>
}