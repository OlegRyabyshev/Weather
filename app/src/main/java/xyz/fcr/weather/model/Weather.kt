package xyz.fcr.weather.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)

data class Current(
    @SerializedName("clouds")
    val clouds: Int,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp")
    val temp: Double,
    @SerializedName("weather")
    val weather: List<Weather>
)

data class Temp(
    @SerializedName("day")
    val day: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("max")
    val max: Double,
    @SerializedName("min")
    val min: Double,
    @SerializedName("morn")
    val morn: Double,
    @SerializedName("night")
    val night: Double
)