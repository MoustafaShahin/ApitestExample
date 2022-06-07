package com.shahin.plan_radar_assessment.utils

import android.app.Application
import android.content.Context


class App : Application(){
    companion object {
        lateinit var context:  Context

        fun getAppContext():Context{
            return context }
    }
 override fun onCreate() {
  super.onCreate()
 context = applicationContext}

 }

