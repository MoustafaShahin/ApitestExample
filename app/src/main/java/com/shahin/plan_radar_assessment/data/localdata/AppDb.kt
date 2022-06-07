package com.shahin.plan_radar_assessment.data.localdata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shahin.plan_radar_assessment.data.dto.local.SavedCity
import com.shahin.plan_radar_assessment.data.dto.local.SavedDetails

@Database(
    entities = [SavedCity::class,SavedDetails::class],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract fun citiesDao():WeatherDao
    companion object {
        private var instance: AppDb? = null
        private val Lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(Lock) {
            instance ?: buildDB(context)
        }

        private fun buildDB(context: Context) =
           Room.databaseBuilder(context,
               AppDb::class.java,
               "plan_radar").build()

    }
}