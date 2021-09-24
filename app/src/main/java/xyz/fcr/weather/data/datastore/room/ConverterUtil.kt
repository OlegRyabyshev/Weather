package xyz.fcr.weather.data.datastore.room

import xyz.fcr.weather.model.City

fun convertToEntity(city: City): CityEntity {
    return CityEntity(0, city.name, city.lat, city.lon)
}

fun convertToEntityList(cityList: List<City>) : MutableList<CityEntity>{
    return cityList.map{
        CityEntity(0, it.name, it.lat, it.lon)
    }.toMutableList()
}

fun convertToCityList(cityEntityList : List<CityEntity>): MutableList<City> {
    return cityEntityList.map {
        City(it.name, it.lat, it.lon)
    }.toMutableList()
}

