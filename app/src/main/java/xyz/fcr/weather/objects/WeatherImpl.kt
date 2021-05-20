package xyz.fcr.weather.objects

class WeatherImpl : WeatherInterface {
    override fun getWeatherData(): WeatherObject {
        return WeatherObject(
            "Moscow",
            "20",
            "32",
            "5"
        )
    }
}