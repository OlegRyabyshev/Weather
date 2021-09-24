package xyz.fcr.weather.presentation

import android.app.Application
import androidx.room.Room
import xyz.fcr.weather.data.datastore.room.CityDao
import xyz.fcr.weather.data.datastore.room.CityDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: App? = null
        private var db: CityDatabase? = null
        private const val DB_NAME = "City.db"

        fun getHistoryDao(): CityDao {
            if (db == null) {
                synchronized(CityDatabase::class.java) {
                    if (db == null) {
                        val error = "Application is null while creating DataBase"
                        if (appInstance == null) throw IllegalStateException(error)
                        db = Room.databaseBuilder(
                            appInstance!!.applicationContext,
                            CityDatabase::class.java,
                            DB_NAME
                        )
                            .allowMainThreadQueries()
                            .build()
                    }
                }
            }
            return db!!.cityDao()
        }
    }
}