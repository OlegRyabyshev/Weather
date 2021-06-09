package xyz.fcr.weather.objects

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
@Entity
data class City(
    val name: String,
    val lat: Double,
    val lon: Double
) : Parcelable {
    var temp: Int = 0
    var maxTemp: Int = 0
    var lowTemp: Int = 0
    var feelsLikeTemp: Int = 0
    var description: String = "Error"
    var hourly: List<Hourly>? = null
    var daily: List<Daily>? = null
    var lastUpd: String = ""
    var icon: String = ""

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

