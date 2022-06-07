package com.shahin.plan_radar_assessment.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahin.plan_radar_assessment.data.Repository
import com.shahin.plan_radar_assessment.data.dto.remoteData.ResponseObject
import com.shahin.plan_radar_assessment.data.dto.local.SavedCity
import com.shahin.plan_radar_assessment.data.dto.local.SavedDetails
import com.shahin.plan_radar_assessment.utils.GeneralResponse.GenericError
import com.shahin.plan_radar_assessment.utils.GeneralResponse.MutableResultWrapper
import com.shahin.plan_radar_assessment.utils.timeStamp
import com.shahin.plan_radar_assessment.utils.toCelisuis
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

class HomeViewModel : ViewModel() {

    private val repo = Repository()
    val searchData = MutableResultWrapper<ResponseObject>()
    val citiesData = MutableLiveData<List<SavedCity>>()


    fun getDetails(name: String) {
        searchData.postLoading()

        viewModelScope.launch(IO) {
            try {
                var r = repo.getRemoteDetails(name)
                if (r != null) {
                    if (r.cod == 200) {
                        searchData.postSuccess(r)
                        repo.insertDetails(
                            SavedDetails(
                                name = r.name.toString(),
                                desc = if (!r.weather.isNullOrEmpty()) {
                                    r.weather!![0].description.toString()
                                } else {
                                    ""
                                },
                                humidity = r.main?.humidity ?: 0.0,
                                wind = r.wind?.speed ?: 0.0,
                                time = timeStamp(),
                                icon = if (!r.weather.isNullOrEmpty()) {
                                    r.weather!![0].icon.toString()
                                } else {
                                    ""
                                },
                                temp = r.main?.temp?.toCelisuis() ?: 0.0
                            )
                        )
                    } else {
                        searchData.postError(
                            GenericError(
                                r.message.toString(),
                                null,
                                r.cod?.toInt(),
                                true
                            )
                        )
                    }
                } else {
                    searchData.postError(GenericError("something went wrong", null, null, false))

                }
            } catch (throwable: Throwable) {
                searchData.postError(
                    GenericError(
                        "something went wrong : ${throwable.message}",
                        throwable,
                        null,
                        false
                    )
                )

            }

        }
    }

    suspend fun getCities() {
        withContext(IO) {
            val result = repo.getCities()
            result.let {
                citiesData.postValue(it)

            }
        }
    }

    suspend fun saveCity(cityName: String) {
        withContext(IO) {
            val result = repo.insertCity(SavedCity(name = cityName))
            result.let {
                getCities()
            }
        }
    }


    suspend fun deleteCity(cityName: String) {
        withContext(IO) {
            val result = repo.deleteCity(SavedCity(name = cityName))
            result.let {
                getCities()
            }
        }
    }

}
