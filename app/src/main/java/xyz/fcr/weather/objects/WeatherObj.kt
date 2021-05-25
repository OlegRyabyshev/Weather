package xyz.fcr.weather.objects

import android.annotation.SuppressLint

data class WeatherObj(
    val cityName: String,
    val lat: String,
    val lon: String,
    val cityDate: String = getCurrentDate(),
    val cityTemp: String = getCurrentTemp(),
    val cityDescription: String = getDescription(),
)

fun getCurrentTemp(): String {
    return "27"
}

fun getDescription(): String {
    return "Mostly Cloudy"
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    //val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
   //val currentDate = sdf.format(Date())
    return ("Fri, 31 July 5:40dp")
}

fun buildArray() {
//
}
