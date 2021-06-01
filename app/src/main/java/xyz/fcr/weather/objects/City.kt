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
    var hourly: List<CityHourly>? = null
    var daily: List<CityDaily>? = null
    var lastUpd: String = ""

    fun feelsLikeLine(): String {
        val sign: String = "°"

        return "$maxTemp" + sign + "/" + "$lowTemp" + sign +
                " Feels like $feelsLikeTemp" + sign
    }

    @SuppressLint("SimpleDateFormat")
    fun lastUpdDate(): String = SimpleDateFormat("E, dd MMM KK:mm a")
        .format(Date())
        .replace("AM", "am")
        .replace("PM","pm")

}

data class CityHourly(
    val clouds: Int,
    val rain: Double,
    val temp: Double
    //val temp: Temp
)

data class CityDaily(
    val clouds: Int,
    val rain: Double,
    val temp: Double
)
