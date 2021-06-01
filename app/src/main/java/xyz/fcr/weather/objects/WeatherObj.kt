package xyz.fcr.weather.objects


data class WeatherObj(
    val cityName: String,
    val cityLat: Double,
    val cityLon: Double
) {
    var cityTemp: Double = 0.0
    var cityFeelsLikeTemp: Double = 0.0
    var cityDescription: String = "Error"
    var cityHourly: List<CityHourly>? = null
    var cityDaily: List<CityDaily>? = null
}

data class CityHourly(
    val clouds: Int,
    val rain: Double,
    val temp: Double
    //val temp: Temp
)

data class CityDaily(
    val clouds: Int,
    val rain: Double,
    val temp: Double
)
