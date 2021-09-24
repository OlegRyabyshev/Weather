package xyz.fcr.weather.fragments.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cities_item.view.*
import xyz.fcr.weather.R
import xyz.fcr.weather.datastore.CitySaver
import xyz.fcr.weather.fragments.adapters.CitiesAdapter.*
import xyz.fcr.weather.objects.City

class CitiesAdapter(
    private val exampleList: List<City>,
    private val activityCities: FragmentActivity?
) :
    RecyclerView.Adapter<CityViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cities_item, parent, false)

        return CityViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currentItem = exampleList[position]
        holder.tvCityName.text = currentItem.name

        holder.itemView.setOnClickListener {
            val manager = activityCities?.supportFragmentManager

            if (manager != null) {
                CitySaver().saveToSharedPref(currentItem, activityCities!!.applicationContext)
                manager.popBackStack()
            }
        }
    }

    override fun getItemCount() = exampleList.size

    inner class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCityName: TextView = itemView.rv_city_name
    }
}