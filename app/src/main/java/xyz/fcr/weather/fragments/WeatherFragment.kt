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
import xyz.fcr.weather.objects.RepositoryImpl
import xyz.fcr.weather.objects.Repository


class WeatherFragment : Fragment() {
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!
    private val repositoryImpl: Repository = RepositoryImpl()
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

        val currentCity = City("Moscow", 55.75, 37.61)

        binding.textviewCity.setOnClickListener {
            val citiesFragment = CitiesFragment()

            val manager = activity?.supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.container, citiesFragment)
                ?.addToBackStack("")
                ?.commit()
        }

        WeatherLoader().loadWeather(currentCity.cityLat, currentCity.cityLon, weatherLiveData)

        weatherLiveData.observe(this, Observer {

            if (it != null) {
                currentCity.cityTemp = it.current.temp.toInt()
                currentCity.cityLowTemp = it.daily[0].temp.min.toInt()
                currentCity.cityMaxTemp = it.daily[0].temp.max.toInt()
                currentCity.cityFeelsLikeTemp = it.current.feelsLike.toInt()
            }

            binding.textviewTemp.text = currentCity.cityTemp.toString()
            binding.textviewFeelsLikeTemp.text = currentCity.feelsLikeLine()

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