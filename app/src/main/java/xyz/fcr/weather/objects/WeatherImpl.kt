package xyz.fcr.weather.objects

class WeatherImpl : WeatherInterface {
    override fun getWeatherData(): WeatherObject {
        return WeatherObject(
            "St. Peter",
            "20",
            "32",
            "10",
        "-20",
            "Raining all day all night everyday"
        )
    }
}