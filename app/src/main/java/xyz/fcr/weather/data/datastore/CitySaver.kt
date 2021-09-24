package xyz.fcr.weather.data.datastore

import android.content.Context
import com.google.gson.Gson
import xyz.fcr.weather.model.City

const val CURRENT_CITY = "current_city"

class CitySaver {

    fun saveToSharedPref(city: City, context: Context) {
        val citySP = context.getSharedPreferences(CURRENT_CITY, Context.MODE_PRIVATE)
        val cityJson = Gson().toJson(city)

        citySP
            .edit()
            .putString(CURRENT_CITY, cityJson)
            .apply()
    }

    fun getFromSharedPref(context: Context): City {
        val citySP = context.getSharedPreferences(CURRENT_CITY, Context.MODE_PRIVATE)
        val cityJson = citySP.getString(CURRENT_CITY, "")

        return if (cityJson == "") {
            City("Moscow", 55.75, 37.61)
        } else {
            Gson().fromJson(cityJson, City::class.java)
        }
    }
}