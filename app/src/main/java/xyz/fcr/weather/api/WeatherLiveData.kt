package xyz.fcr.weather.api

import androidx.lifecycle.LiveData
import xyz.fcr.weather.api.dto.WeatherDTO

class WeatherLiveData : LiveData<WeatherDTO?>() {

    override fun onActive() {
        println("Woke up")
        super.onActive()
    }

    override fun onInactive() {
        println("Sleeping")
        super.onInactive()
    }

    public override fun postValue(value: WeatherDTO?) {
        super.postValue(value)
    }
}