package xyz.fcr.weather.objects

class CityList {
    val list: List<City> = getListOfCities()

    private fun getListOfCities(): List<City> {
        return listOf(
            City("Moscow", 55.75, 37.61),
            City("Saint Petersburg", 59.932, 30.33),
            City("London", 51.50, -0.12),
            City("Tokyo", 35.68, 139.69),
            City("Paris", 48.85, 2.34),
            City("Berlin", 52.529, 13.40),
            City("Rim", 41.9027835, 12.496365500000024),
            City("Minsk", 53.90, 27.56),
            City("Washington", 38.90, -77.03),
            City("Kiev", 50.45, 30.52),
            City("Beijing", 39.90, 116.40),
            City("Duduinka", 69.24, 86.11),
        )
    }
}