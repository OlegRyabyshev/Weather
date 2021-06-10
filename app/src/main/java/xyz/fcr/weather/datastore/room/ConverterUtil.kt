package xyz.fcr.weather.datastore.room

import xyz.fcr.weather.objects.City
import xyz.fcr.weather.objects.CityList

fun convertToEntity(city: City): CityEntity {
    return CityEntity(0, city.name, city.lat, city.lon)
}

fun convertToEntityList(cityList: List<City>) : List<CityEntity>{
    return cityList.map{
        CityEntity(0, it.name, it.lat, it.lon)
    }
}

fun convertToCity(cityEntity: CityEntity): City {
    return City(cityEntity.name, cityEntity.lat, cityEntity.lon)
}

fun convertToCityList(cityEntityList : List<CityEntity>): List<City> {
    return cityEntityList.map {
        City(it.name, it.lat, it.lon)
    }
}

