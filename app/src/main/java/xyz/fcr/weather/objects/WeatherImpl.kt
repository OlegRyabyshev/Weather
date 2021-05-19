package xyz.fcr.weather.objects

class WeatherImpl : WeatherInterface {
    override fun getWeatherData(): WeatherObject {
        return WeatherObject(
            "Moscow",
            "20 C",
            "32 C",
            "5 C"
        )
    }
}