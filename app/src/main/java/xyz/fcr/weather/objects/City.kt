package xyz.fcr.weather.objects

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

data class City(
    val name: String,
    val lat: Double,
    val lon: Double
) {
    var temp: Int = 0
    var maxTemp: Int = 0
    var lowTemp: Int = 0
    var feelsLikeTemp: Int = 0
    var description: String = "Error"
    var hourly: List<Hourly>? = null
    var daily: List<Daily>? = null
    var lastUpd: String = ""

    fun feelsLikeLine(): String {
        return "$maxTemp°/$lowTemp° Feels like $feelsLikeTemp°"
    }

    @SuppressLint("SimpleDateFormat")
    fun updateDateInfo() {
        lastUpd = SimpleDateFormat("E, dd MMM KK:mm a")
            .format(Date())
            .replace("AM", "am")
            .replace("PM", "pm")

    }

}

