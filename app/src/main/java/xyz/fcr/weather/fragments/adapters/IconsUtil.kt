package xyz.fcr.weather.fragments.adapters

import xyz.fcr.weather.R

fun loadPicture(icon: String, dark: Boolean): Int {

    if (dark){
        when(icon){
            "01d" -> return R.drawable.ic__01d_black
            "01n" -> return R.drawable.ic__01n_black
            "02d" -> return R.drawable.ic__02d_black
            "02n" -> return R.drawable.ic__02n_black
            "03d" -> return R.drawable.ic__03d_black
            "03n" -> return R.drawable.ic__03d_black
            "04d" -> return R.drawable.ic__04d_black
            "04n" -> return R.drawable.ic__04d_black
            "09d" -> return R.drawable.ic__09d_black
            "09n" -> return R.drawable.ic__09n_black
            "10d" -> return R.drawable.ic__09d_black
            "10n" -> return R.drawable.ic__09n_black
            "11d" -> return R.drawable.ic__11d_black
            "11n" -> return R.drawable.ic__11n_black
            "13d" -> return R.drawable.ic__13d_black
            "13n" -> return R.drawable.ic__13n_black
            "50d" -> return R.drawable.ic__50d_black
            "50n" -> return R.drawable.ic__50d_black
        }

        return R.drawable.ic_na
    }

    when(icon){
        "01d" -> return R.drawable.ic__01d
        "01n" -> return R.drawable.ic__01n
        "02d" -> return R.drawable.ic__02d
        "02n" -> return R.drawable.ic__02n
        "03d" -> return R.drawable.ic__03d
        "03n" -> return R.drawable.ic__03d
        "04d" -> return R.drawable.ic__04d
        "04n" -> return R.drawable.ic__04d
        "09d" -> return R.drawable.ic__09d
        "09n" -> return R.drawable.ic__09n
        "10d" -> return R.drawable.ic__09d
        "10n" -> return R.drawable.ic__09n
        "11d" -> return R.drawable.ic__11d
        "11n" -> return R.drawable.ic__11n
        "13d" -> return R.drawable.ic__13d
        "13n" -> return R.drawable.ic__13n
        "50d" -> return R.drawable.ic__50d
        "50n" -> return R.drawable.ic__50d
    }

    return R.drawable.ic_na
}