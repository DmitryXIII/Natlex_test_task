package com.avacodo.natlextesttask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.avacodo.natlextesttask.data.local.entity.WeatherLocalEntity

private const val DB_NAME = "WeatherDatabase"

@Database(
    entities = [WeatherLocalEntity::class],
    version = 1,
    exportSchema = true)
abstract class WeatherDatabase: RoomDatabase() {
    abstract val weatherDao: WeatherDao

    companion object {
        private var INSTANCE: WeatherDatabase? = null

        fun getUserDatabase(context: Context): WeatherDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    WeatherDatabase::class.java,
                    DB_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}