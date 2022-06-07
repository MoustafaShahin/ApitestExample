package com.shahin.plan_radar_assessment.data

import com.shahin.plan_radar_assessment.data.dto.local.SavedCity
import com.shahin.plan_radar_assessment.data.dto.local.SavedDetails
import com.shahin.plan_radar_assessment.data.localdata.AppDb
import com.shahin.plan_radar_assessment.data.localdata.WeatherDao
import com.shahin.plan_radar_assessment.utils.App
import com.task.data.remote.RemoteData

class Repository  {
    private  var weatherDao: WeatherDao = AppDb(App.getAppContext()
    ).citiesDao()
 private val remoteRepository= RemoteData()

    suspend fun getRemoteDetails(cityName:String)= remoteRepository.requestDetails(cityName)

    suspend fun getCities() = weatherDao.getCities()
    suspend fun deleteCity(item : SavedCity) = weatherDao.deleteCity(item)
    suspend fun insertCity(item: SavedCity) = weatherDao.insertCity(item)

    suspend fun getDetails(name:String)  = weatherDao.getDetailsList(name)
    suspend fun deleteDetails(name: String)= weatherDao.deleteDetails(name)
    suspend fun insertDetails(item: SavedDetails) = weatherDao.insertDetails(item)

}