package xyz.fcr.weather.objects

class WeatherImpl : WeatherInterface {
    override fun getWeatherData(): WeatherObject {
        return WeatherObject("New York City", "20", "32")
    }
}