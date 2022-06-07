package com.shahin.plan_radar_assessment.data.remote.service

import com.shahin.plan_radar_assessment.data.dto.remoteData.ResponseObject
import com.shahin.plan_radar_assessment.utils.DETAILS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {
    @GET(DETAILS)
    suspend fun fetchDetails(
        @Query("q") cityName:String,
        @Query("appid") appId:String
    ): Response<ResponseObject>
}
