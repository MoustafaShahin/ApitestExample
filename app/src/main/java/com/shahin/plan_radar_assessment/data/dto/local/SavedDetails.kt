package com.shahin.plan_radar_assessment.data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "SavedDetails")
data class SavedDetails(
    @ColumnInfo(name= "name") val name: String,
    @ColumnInfo(name = "desc") val desc:String,
    @ColumnInfo(name = "humidity") val humidity:Double,
    @ColumnInfo(name="wind") val wind:Double,
    @ColumnInfo(name="time") val time:String,
    @ColumnInfo(name ="icon") val icon :String,
    @ColumnInfo(name = "temp") val temp:Double,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") var id:Int =0
):Serializable
