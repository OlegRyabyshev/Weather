package xyz.fcr.weather.data.api

import androidx.lifecycle.LiveData
import xyz.fcr.weather.model.WeatherDTO

class WeatherLiveData : LiveData<WeatherDTO?>() {
    public override fun postValue(value: WeatherDTO?) {
        super.postValue(value)
    }
}