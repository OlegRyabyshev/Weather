package xyz.fcr.weather.objects

class WeatherImpl : WeatherInterface {
    override fun getWeatherData(): WeatherObj {
        return WeatherObj("New York", 20.0, 32.0)
    }
}