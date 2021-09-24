package xyz.fcr.weather.data.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import xyz.fcr.weather.model.WeatherDTO

interface WeatherAPI {
    @GET("data/2.5/onecall?")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String,
        @Query("appid") api: String,
        @Query("units") units: String
    ): Call<WeatherDTO>
}