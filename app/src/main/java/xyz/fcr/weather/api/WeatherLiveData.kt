package xyz.fcr.weather.api

import androidx.lifecycle.LiveData
import xyz.fcr.weather.objects.WeatherDTO

class WeatherLiveData : LiveData<WeatherDTO?>() {
    public override fun postValue(value: WeatherDTO?) {
        super.postValue(value)
    }
}