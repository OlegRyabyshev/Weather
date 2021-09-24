package xyz.fcr.weather.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class City(
    val name: String,
    val lat: Double,
    val lon: Double,
    var temp: Int = 0,
    var maxTemp: Int = 0,
    var lowTemp: Int = 0,
    var feelsLikeTemp: Int = 0,
    var description: String = "Error",
    var hourly: @RawValue List<Hourly>? = null,
    var daily: @RawValue List<Daily>? = null,
    var lastUpd: String = "",
    var icon: String = ""
) : Parcelable {

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

