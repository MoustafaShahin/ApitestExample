package com.shahin.plan_radar_assessment.utils.GeneralResponse

data class GenericError(var msg:String?=null, var throwable: Throwable?=null, var responseCode:Int?=null, var isToast:Boolean=true)