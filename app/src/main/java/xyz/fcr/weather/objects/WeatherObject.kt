package xyz.fcr.weather.objects

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

data class WeatherObject(
    val cityName: String,
    val cityTemp: String,
    val cityHighestTemp: String,
    val cityLowestTemp: String,
    val cityFeelsLikeTemp: String,
    val cityDescription: String,
) {
    init {
        val cityDate: String = getCurrentDate()
    }
}

@SuppressLint("SimpleDateFormat")
fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
    val currentDate = sdf.format(Date())
    return ("Date$currentDate")
}
