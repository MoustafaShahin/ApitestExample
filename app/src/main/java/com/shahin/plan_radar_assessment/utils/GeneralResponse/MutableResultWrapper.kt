package com.shahin.plan_radar_assessment.utils.GeneralResponse

import androidx.lifecycle.MutableLiveData


class MutableResultWrapper<T> : MutableLiveData<ResultWrapper<T>?>() {
    fun postLoading() {
        postValue(ResultWrapper<T>().loading())
    }

    fun postError(error: GenericError?) {
        postValue(ResultWrapper<T>().error(error))
    }

    fun postSuccess(data: T) {
        postValue(ResultWrapper<T>().success(data))
    }


}
