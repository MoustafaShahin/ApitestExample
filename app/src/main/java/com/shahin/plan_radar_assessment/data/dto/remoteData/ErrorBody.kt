package com.shahin.plan_radar_assessment.data.dto.remoteData


import com.google.gson.annotations.SerializedName

data class ErrorBody(
    @SerializedName("cod")
    var cod: String?,
    @SerializedName("message")
    var message: String?
)