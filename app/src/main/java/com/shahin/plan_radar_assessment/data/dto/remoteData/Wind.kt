package com.shahin.plan_radar_assessment.data.dto.remoteData


import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("deg")
    var deg: Double?,
    @SerializedName("speed")
    var speed: Double?
)