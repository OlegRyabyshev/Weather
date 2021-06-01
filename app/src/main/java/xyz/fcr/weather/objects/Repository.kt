package xyz.fcr.weather.objects

interface Repository {
    fun getWeatherDataFromApi() : City
    fun getWeatherDataFromStorage() : City
}