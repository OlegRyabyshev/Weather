package xyz.fcr.weather.api.dto


import com.google.gson.annotations.SerializedName

data class Hourly(
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("weather")
    val weather: List<Weather>
)