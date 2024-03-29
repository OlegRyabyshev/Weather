package xyz.fcr.weather.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.fcr.weather.R
import xyz.fcr.weather.data.api.RemoteDataSource
import xyz.fcr.weather.data.api.WeatherLiveData
import xyz.fcr.weather.databinding.WeatherFragmentBinding
import xyz.fcr.weather.data.datastore.CitySaver
import xyz.fcr.weather.presentation.fragments.adapters.*
import xyz.fcr.weather.model.City
import xyz.fcr.weather.model.WeatherDTO
import xyz.fcr.weather.util.loadPicture
import kotlin.math.roundToInt

class WeatherFragment : Fragment() {
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!

    private val weatherLiveData = WeatherLiveData()
    private val remoteDataSource = RemoteDataSource()

    private lateinit var tabLayout: TabLayout
    private lateinit var pager2: ViewPager2
    private lateinit var adapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = CitySaver().getFromSharedPref(requireContext())

        binding.textviewCity.setOnClickListener {
            val mapFragment = MapsFragment()

            val manager = activity?.supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.container, mapFragment)
                ?.addToBackStack("")
                ?.commit()
        }

        binding.buttonEditCity.setOnClickListener {
            val citiesFragment = CitiesFragment()

            val manager = activity?.supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.container, citiesFragment)
                ?.addToBackStack("")
                ?.commit()
        }

        val callBack = object :
            Callback<WeatherDTO> {

            override fun onResponse(call: Call<WeatherDTO>, response: Response<WeatherDTO>) {
                val serverResponse: WeatherDTO? = response.body()
                weatherLiveData.postValue(serverResponse)
            }

            override fun onFailure(call: Call<WeatherDTO>, t: Throwable) {
                Toast.makeText(
                    context,
                    "Can't access weather server \nCheck internet connection",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }

        remoteDataSource.getWeatherDetails(city.lat, city.lon, callBack)

        weatherLiveData.observe(viewLifecycleOwner, {
            loadWeatherUI(it, city)
            CitySaver().saveToSharedPref(city, requireContext())
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadWeatherUI(weatherDTO: WeatherDTO?, city: City) {
        weatherDTO?.apply {
            city.temp = current.temp.roundToInt()
            city.lowTemp = daily[0].temp.min.roundToInt()
            city.maxTemp = daily[0].temp.max.roundToInt()
            city.feelsLikeTemp = current.feelsLike.roundToInt()
            city.description = current.weather[0].description
            city.icon = current.weather[0].icon
            city.updateDateInfo()

            city.hourly = hourly
            city.daily = daily
        }

        binding.apply {
            textviewCity.text = city.name
            textviewTemp.text = city.temp.toString()
            textviewFeelsLikeTemp.text = city.feelsLikeLine()
            textviewDate.text = city.lastUpd
            textviewDescription.text = city.description.replaceFirstChar { char ->
                char.uppercaseChar()
            }

            weatherImage.setImageResource(loadPicture(city.icon, false))
        }

        tabLayout = binding.tabLayout
        pager2 = binding.viewPager2

        adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        pager2.adapter = adapter

        TabLayoutMediator(tabLayout, pager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Today"
                }
                1 -> {
                    tab.text = "This week"
                }
            }
        }.attach()

        pager2.isUserInputEnabled = false
    }
}