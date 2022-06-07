package com.shahin.plan_radar_assessment.utils

import android.provider.ContactsContract
import android.widget.ImageView
import coil.load
import com.shahin.plan_radar_assessment.R
import java.text.SimpleDateFormat
import java.util.*


fun timeStamp():String {
    val calendar= Calendar.getInstance()
    val now : Date = calendar.time
    val format = SimpleDateFormat("dd.MM.yyyy - HH:mm", Locale("en"))
    val timeStamp = format.format(now)
    return timeStamp

}
fun Double.toCelisuis(): Double{
    var c = this - 273.15
    return "%.2f".format(c).toDouble()
}
fun ImageView.bind(icon :String){
    if (!icon.isNullOrEmpty()){
        this.load("http://openweathermap.org/img/w/$icon.png"){
            crossfade(true)
            placeholder(R.drawable.ic_image)
        }
    }

}