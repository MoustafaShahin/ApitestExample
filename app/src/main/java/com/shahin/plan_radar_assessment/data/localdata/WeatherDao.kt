package com.shahin.plan_radar_assessment.data.localdata

import androidx.room.*
import com.shahin.plan_radar_assessment.data.dto.local.SavedCity
import com.shahin.plan_radar_assessment.data.dto.local.SavedDetails

@Dao
interface WeatherDao {
    @Query("Select * From SavedCity")
    suspend fun getCities():List<SavedCity>
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(item: SavedCity):Long
    @Delete
    suspend fun deleteCity(item: SavedCity):Int

    @Query("Select * From SavedDetails Where name = :city")
    suspend fun getDetailsList(city:String):List<SavedDetails>
@Insert(onConflict = OnConflictStrategy.IGNORE)
suspend fun insertDetails(item:SavedDetails):Long
    @Query("DELETE FROM SavedDetails WHERE name = :city")
    suspend fun deleteDetails(city: String):Int

}
