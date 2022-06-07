package com.shahin.plan_radar_assessment.ui.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.shahin.plan_radar_assessment.data.Repository
import com.shahin.plan_radar_assessment.data.dto.local.SavedCity
import com.shahin.plan_radar_assessment.data.dto.local.SavedDetails
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryViewModel : ViewModel() {

    private val repo= Repository()
    val historyData = MutableLiveData<List<SavedDetails>>()

    suspend fun getHistory(cityName:String){
        withContext(Dispatchers.IO){
            val result = repo.getDetails(cityName)
            result.let {
                historyData.postValue( it)
            }
        }
    }
}
