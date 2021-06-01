package xyz.fcr.weather.objects


data class City(
    val cityName: String,
    val cityLat: Double,
    val cityLon: Double
) {
    var cityTemp: Int = 0
    var cityMaxTemp: Int = 0
    var cityLowTemp: Int = 0
    var cityFeelsLikeTemp: Int = 0
    var cityDescription: String = "Error"
    var cityHourly: List<CityHourly>? = null
    var cityDaily: List<CityDaily>? = null

    fun feelsLikeLine(): String {
        val sign: String = "°"

        return "$cityMaxTemp" + sign + "/" + "$cityLowTemp" + sign +
                " Feels like $cityFeelsLikeTemp" + sign
    }
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
