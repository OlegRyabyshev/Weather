package xyz.fcr.weather.fragments.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.weather_item.view.*
import xyz.fcr.weather.R
import xyz.fcr.weather.objects.Hourly
import kotlin.math.roundToInt

const val MAX_ITEMS_SHOWN : Int = 25

class HourlyAdapter(private val hourlyList: List<Hourly>) :
    RecyclerView.Adapter<HourlyAdapter.HourlyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)

        return HourlyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HourlyViewHolder, position: Int) {
        val currentItem = hourlyList[position]
        val temperature = currentItem.temp.roundToInt().toString() + "°"

        holder.textViewTime.text = unixToTime(currentItem.dt)
        holder.textViewTemp.text = temperature
        holder.imageViewWeather.setImageResource(loadPicture(currentItem.weather[0].icon, true))
    }

    @SuppressLint("SimpleDateFormat")
    private fun unixToTime(unixDT: Int): String {
        val simpleDF = java.text.SimpleDateFormat("HH:mm")
        val date = java.util.Date(unixDT * 1000L)
        return simpleDF.format(date)
    }

    class HourlyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTime: TextView = itemView.rv_weather_time
        val textViewTemp: TextView = itemView.rv_weather_temp
        val imageViewWeather: ImageView = itemView.rv_weather_image
    }

    override fun getItemCount() : Int {
        return if (hourlyList.size > MAX_ITEMS_SHOWN) MAX_ITEMS_SHOWN
        else hourlyList.size
    }
}