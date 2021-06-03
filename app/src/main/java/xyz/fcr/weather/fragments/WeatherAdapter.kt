package xyz.fcr.weather.fragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_item.view.*
import xyz.fcr.weather.R
import xyz.fcr.weather.fragments.WeatherAdapter.*
import xyz.fcr.weather.objects.Hourly
import kotlin.math.roundToInt

class WeatherAdapter(private val hourlyList: List<Hourly>) :
    RecyclerView.Adapter<WeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
            val currentItem = hourlyList[position]

            holder.textViewTime.text = unixToTime(currentItem.dt)
            holder.textViewTemp.text = currentItem.temp.roundToInt().toString()
            holder.imageViewWeather
    }

    @SuppressLint("SimpleDateFormat")
    private fun unixToTime(unixDT: Int): String {
        val simpleDF = java.text.SimpleDateFormat("HH:mm")
        val date = java.util.Date(unixDT * 1000L)
        return simpleDF.format(date)
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTime: TextView = itemView.rv_weather_time
        val textViewTemp: TextView = itemView.rv_weather_temp
        val imageViewWeather: ImageView = itemView.rv_weather_image
    }

    override fun getItemCount() = hourlyList.size


}