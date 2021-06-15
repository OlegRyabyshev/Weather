package xyz.fcr.weather.datastore.room

import androidx.room.*

@Dao
interface CityDao {
    @Query("SELECT * FROM cities")
    fun getListOfCities() : List<CityEntity>

    @Update
    fun updateCity(entity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addCity(entity: CityEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addListCity(listCity: List<CityEntity>)

    @Delete
    fun deleteCity(entity: CityEntity)
}