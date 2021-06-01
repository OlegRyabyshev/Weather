package xyz.fcr.weather.objects

class RepositoryImpl : Repository {
    override fun getWeatherDataFromApi(): City {
        return City("Moscow", 55.75, 37.61)
    }

    override fun getWeatherDataFromStorage(): City {
        return City("Moscow", 55.75, 37.61)
    }
}