package xyz.fcr.weather.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.weather_fragment.*
import xyz.fcr.weather.R
import xyz.fcr.weather.api.WeatherLoader
import xyz.fcr.weather.databinding.WeatherFragmentBinding
import xyz.fcr.weather.objects.WeatherImpl
import xyz.fcr.weather.objects.WeatherInterface
import xyz.fcr.weather.objects.WeatherObj
import xyz.fcr.weather.recyclerview.WeatherAdapter


class WeatherFragment : Fragment() {
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!
    private val weatherImpl: WeatherInterface = WeatherImpl()

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

        binding.textviewCity.setOnClickListener {
            val citiesFragment = CitiesFragment()

            val manager = activity?.supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.container, citiesFragment)
                ?.addToBackStack("")
                ?.commit()
        }

        val exampleList = generateDummyList(40)
        recycler_view_weather.adapter = WeatherAdapter(exampleList)
        recycler_view_weather.setHasFixedSize(true)

        val weather = WeatherObj("Moscow", "55.7522", "37.6156", "-2","Super cloudy")
        WeatherLoader().loadWeather(weather.cityName)
    }

    private fun generateDummyList(size: Int): List<WeatherObj> {
        val list = ArrayList<WeatherObj>()
        for (i in 0 until size) {
            val item = WeatherObj("Moscow", "13", "123", "25","Super cloudy")
            list += item
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}