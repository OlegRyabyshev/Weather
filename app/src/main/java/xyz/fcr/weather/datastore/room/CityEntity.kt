package xyz.fcr.weather.datastore.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "cities")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val lat: Double,
    val lon: Double,
)

