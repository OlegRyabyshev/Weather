package xyz.fcr.weather.datastore.room

import androidx.room.*
import xyz.fcr.weather.objects.City


@Dao
interface CityDao {
    @Query("SELECT * FROM cities")
    fun getListOfCities() : List<City>

    @Update
    fun updateCity(city: City)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(city: City)

    @Delete
    fun deleteCity(city: City)
}