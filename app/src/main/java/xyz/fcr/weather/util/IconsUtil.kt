package xyz.fcr.weather.fragments.adapters

import xyz.fcr.weather.R

fun loadPicture(icon: String, dark: Boolean): Int {

    if (dark){
        when(icon){
            "01d" -> return R.drawable.ic_01d_black
            "01n" -> return R.drawable.ic_01n_black
            "02d" -> return R.drawable.ic_02d_black
            "02n" -> return R.drawable.ic_02n_black
            "03d" -> return R.drawable.ic_03d_black
            "03n" -> return R.drawable.ic_03d_black
            "04d" -> return R.drawable.ic_04d_black
            "04n" -> return R.drawable.ic_04d_black
            "09d" -> return R.drawable.ic_09d_black
            "09n" -> return R.drawable.ic_09n_black
            "10d" -> return R.drawable.ic_09d_black
            "10n" -> return R.drawable.ic_09n_black
            "11d" -> return R.drawable.ic_11d_black
            "11n" -> return R.drawable.ic_11n_black
            "13d" -> return R.drawable.ic_13d_black
            "13n" -> return R.drawable.ic_13n_black
            "50d" -> return R.drawable.ic_50d_black
            "50n" -> return R.drawable.ic_50d_black
        }

        return R.drawable.ic_na
    }

    when(icon){
        "01d" -> return R.drawable.ic_01d
        "01n" -> return R.drawable.ic_01n
        "02d" -> return R.drawable.ic_02d
        "02n" -> return R.drawable.ic_02n
        "03d" -> return R.drawable.ic_03d
        "03n" -> return R.drawable.ic_03d
        "04d" -> return R.drawable.ic_04d
        "04n" -> return R.drawable.ic_04d
        "09d" -> return R.drawable.ic_09d
        "09n" -> return R.drawable.ic_09n
        "10d" -> return R.drawable.ic_09d
        "10n" -> return R.drawable.ic_09n
        "11d" -> return R.drawable.ic_11d
        "11n" -> return R.drawable.ic_11n
        "13d" -> return R.drawable.ic_13d
        "13n" -> return R.drawable.ic_13n
        "50d" -> return R.drawable.ic_50d
        "50n" -> return R.drawable.ic_50d
    }

    return R.drawable.ic_na
}