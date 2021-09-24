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
import xyz.fcr.weather.objects.Daily
import xyz.fcr.weather.util.loadPicture
import kotlin.math.roundToInt

class DailyAdapter(private val dailyList: List<Daily>) :
    RecyclerView.Adapter<DailyAdapter.DailyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.weather_item, parent, false)

        return DailyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        val currentItem = dailyList[position]
        val temperature = currentItem.temp.max.roundToInt().toString() + "°"

        holder.textViewTime.text = toDate(currentItem.dt)
        holder.textViewTemp.text = temperature
        holder.imageViewWeather.setImageResource(
            loadPicture(currentItem.weather[0].icon, true)
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun toDate(time: Int): String {
        val simpleDF = java.text.SimpleDateFormat("E")
        val date = java.util.Date(time * 1000L)
        return simpleDF.format(date)
    }

    class DailyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTime: TextView = itemView.rv_weather_time
        val textViewTemp: TextView = itemView.rv_weather_temp
        val imageViewWeather: ImageView = itemView.rv_weather_image
    }

    override fun getItemCount() = dailyList.size
}