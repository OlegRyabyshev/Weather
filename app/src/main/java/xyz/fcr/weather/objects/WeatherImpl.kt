package xyz.fcr.weather.objects

class WeatherImpl : WeatherInterface {
    override fun getWeatherData(): WeatherObj {
        return WeatherObj("New York City", "20", "32")
    }
}