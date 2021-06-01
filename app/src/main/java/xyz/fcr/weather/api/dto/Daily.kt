package xyz.fcr.weather.api.dto


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("weather")
    val weather: List<Weather>
)