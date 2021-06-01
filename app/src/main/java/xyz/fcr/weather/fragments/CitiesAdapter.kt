package xyz.fcr.weather.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cities_item.view.*
import xyz.fcr.weather.R
import xyz.fcr.weather.objects.WeatherObj
import xyz.fcr.weather.fragments.CitiesAdapter.*


class CitiesAdapter(private val exampleList: List<WeatherObj>) :
    RecyclerView.Adapter<WeatherViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cities_item, parent, false)
        return WeatherViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val currentItem = exampleList[position]

        holder.tvCityName.text = "Moscow"
    }

    override fun getItemCount() = exampleList.size

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCityName: TextView = itemView.rv_city_name
    }
}