package xyz.fcr.weather.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_item.view.*
import xyz.fcr.weather.R
import xyz.fcr.weather.fragments.WeatherAdapter.*
import xyz.fcr.weather.objects.City


class WeatherAdapter(private val exampleList: List<City>) :
    RecyclerView.Adapter<WeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = exampleList[position]
//        holder.textViewTime.text = currentItem.text1
//        holder.textViewTemp.text = currentItem.text2
//        holder.imageView.setImageResource(currentItem.imageResource)


        holder.textViewTime.text = "12:00"
        holder.textViewTemp.text = "25°"
    }

    override fun getItemCount() = exampleList.size

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTime: TextView = itemView.rv_weather_time
        val textViewTemp: TextView = itemView.rv_weather_temp
        val imageViewWeather: ImageView = itemView.rv_weather_image
    }
}