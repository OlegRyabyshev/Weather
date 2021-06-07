package xyz.fcr.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.weather_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import xyz.fcr.weather.R
import xyz.fcr.weather.api.WeatherLiveData
import xyz.fcr.weather.api.RemoteDataSource
import xyz.fcr.weather.databinding.WeatherFragmentBinding
import xyz.fcr.weather.objects.City
import xyz.fcr.weather.objects.WeatherDTO
import kotlin.math.roundToInt


class WeatherFragment : Fragment() {
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!
    private val weatherLiveData = WeatherLiveData()
    private val remoteDataSource = RemoteDataSource()

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

        var city = City("Moscow", 55.75, 37.61)

        if (savedInstanceState?.containsKey("current_city") == true) {
            city = savedInstanceState.getParcelable("current_city")!!
        }

        binding.textviewCity.setOnClickListener {
            val citiesFragment = CitiesFragment()

            val manager = activity?.supportFragmentManager
            manager
                ?.beginTransaction()
                ?.replace(R.id.container, citiesFragment)
                ?.addToBackStack("")
                ?.commit()
        }

        //WeatherLoader().loadWeather(city.lat, city.lon, weatherLiveData)

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
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }

        remoteDataSource.getWeatherDetails(city.lat, city.lon, callBack)

        weatherLiveData.observe(this, {

            //Copying info from API to City object
            it?.apply {
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
                textviewTemp.text = city.temp.toString()
                textviewFeelsLikeTemp.text = city.feelsLikeLine()
                textviewDate.text = city.lastUpd
                textviewDescription.text = city.description.replaceFirstChar { char ->
                    char.uppercaseChar()
                }

                Glide
                    .with(requireContext())
                    .load("https://openweathermap.org/img/wn/${city.icon}@2x.png")
                    .into(weatherImage)

            }

            if (city.hourly != null) {
                recycler_view_weather.adapter = WeatherAdapter(city.hourly!!)
                recycler_view_weather.setHasFixedSize(true)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}