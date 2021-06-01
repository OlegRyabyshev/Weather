package xyz.fcr.weather.api.dto


import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("current")
    val current: Current,

    @SerializedName("daily")
    val daily: List<Daily>,

    @SerializedName("hourly")
    val hourly: List<Hourly>,

    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lon")
    val lon: Double
)