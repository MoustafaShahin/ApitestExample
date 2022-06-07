package com.shahin.plan_radar_assessment.data.dto.remoteData


import com.google.gson.annotations.SerializedName

data class ResponseObject(
    @SerializedName("cod")
    var cod: Int?,
    @SerializedName("id")
    var id: Int?,
    @SerializedName("main")
    var main: Main?,
    @SerializedName("message")
    var message: String?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("sys")
    var sys: Sys?,
    @SerializedName("timezone")
    var timezone: Int?,
    @SerializedName("weather")
    var weather: List<Weather>?,
    @SerializedName("wind")
    var wind: Wind?
)