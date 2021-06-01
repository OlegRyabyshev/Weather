package xyz.fcr.weather.api.dto


import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("current")
    val current: Current,

    @SerializedName("daily")
    val daily: List<Daily>,

    @SerializedName("hourly")
    val hourly: List<Hourly>
)

data class Hourly(
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("weather")
    val weather: List<Weather>
)

data class Daily(
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("weather")
    val weather: List<Weather>
)