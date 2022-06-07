package com.task.data.remote

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.shahin.plan_radar_assessment.data.dto.remoteData.ResponseObject
import com.shahin.plan_radar_assessment.data.dto.remoteData.ErrorBody
import com.shahin.plan_radar_assessment.data.remote.RemoteDataSource
import com.shahin.plan_radar_assessment.data.remote.ServiceGenerator
import com.shahin.plan_radar_assessment.data.remote.service.WeatherService
import com.shahin.plan_radar_assessment.utils.APP_ID


class RemoteData
 : RemoteDataSource {
    private val serviceGenerator= ServiceGenerator()
    override suspend fun requestDetails(cityName:String): ResponseObject? {

        val weatherService = serviceGenerator.createService(WeatherService::class.java)
        val  response =(weatherService::fetchDetails).invoke(cityName, APP_ID)
         if (response.isSuccessful){
return  response.body()
        }else{
            val gson = Gson()
            val type = object : TypeToken<ErrorBody>() {}.type
              val errorBody :ErrorBody? =  gson.fromJson(response.errorBody()!!.charStream().readText().toString(),type)
         var ret =   ResponseObject(cod = errorBody?.cod.toString().toInt(), message = errorBody?.message, id = null, main = null, name = null, sys = null, timezone = null, weather = null, wind = null)

        return    ret
        }


    }


}
