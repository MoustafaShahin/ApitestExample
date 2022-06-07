package com.shahin.plan_radar_assessment.data.dto.remoteData


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("humidity")
    var humidity: Double?,
    @SerializedName("temp")
    var temp: Double?
)