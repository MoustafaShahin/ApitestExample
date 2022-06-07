package com.shahin.plan_radar_assessment.data.remote

import com.shahin.plan_radar_assessment.data.dto.remoteData.ResponseObject



internal interface RemoteDataSource {
    suspend fun requestDetails(cityName:String): ResponseObject?
}
