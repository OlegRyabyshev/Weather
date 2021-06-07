package xyz.fcr.weather.api

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import xyz.fcr.weather.api.retrofit.WeatherAPI
import xyz.fcr.weather.objects.WeatherDTO

class RemoteDataSource {
    private val weatherApi = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(WeatherAPI::class.java)

    fun getWeatherDetails(lat: Double, lon: Double, callback: Callback<WeatherDTO>) {
        val apiKey = "a73c646cde5c5a1ec0adc33aebba434f"
        val exclude = "minutely,alerts"
        val units = "metric"

        weatherApi.getWeather(lat, lon, exclude, apiKey, units).enqueue(callback)
    }
}