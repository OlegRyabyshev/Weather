package xyz.fcr.weather.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.weather_fragment.*
import xyz.fcr.weather.R
import xyz.fcr.weather.api.WeatherLiveData
import xyz.fcr.weather.api.WeatherLoader
import xyz.fcr.weather.databinding.WeatherFragmentBinding
import xyz.fcr.weather.objects.City
import kotlin.math.roundToInt


class WeatherFragment : Fragment() {
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!
    private val weatherLiveData = WeatherLiveData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = City("Moscow", 55.75, 37.61)

        binding.textviewCity.setOnClickListener {
            val citiesFragment = CitiesFragment()

            val manager = activity?.supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.container, citiesFragment)
                ?.addToBackStack("")
                ?.commit()
        }

        WeatherLoader().loadWeather(city.lat, city.lon, weatherLiveData)

        weatherLiveData.observe(this, Observer {

            //Copying info from API to City object
            it?.apply {
                city.temp = current.temp.roundToInt()
                city.lowTemp = daily[0].temp.min.roundToInt()
                city.maxTemp = daily[0].temp.max.roundToInt()
                city.feelsLikeTemp = current.feelsLike.roundToInt()
                city.updateDateInfo()
            }

            binding.apply {
                textviewTemp.text = city.temp.toString()
                textviewFeelsLikeTemp.text = city.feelsLikeLine()
                textviewDate.text = city.lastUpd

            }

            val exampleList = generateDummyList(40)
            recycler_view_weather.adapter = WeatherAdapter(exampleList)
            recycler_view_weather.setHasFixedSize(true)
        })
    }

    private fun generateDummyList(size: Int): List<City> {
        val list = ArrayList<City>()
        for (i in 0 until size) {
            val item = City("Moscow", 55.75, 37.61)
            list += item
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}