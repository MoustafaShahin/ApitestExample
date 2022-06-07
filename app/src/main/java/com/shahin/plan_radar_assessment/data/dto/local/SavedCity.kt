package com.shahin.plan_radar_assessment.data.dto.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedCity")

data class SavedCity(
    @PrimaryKey
   @ColumnInfo(name= "name") val name: String
    )
