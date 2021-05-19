package xyz.fcr.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.fcr.weather.databinding.WeatherFragmentBinding
import xyz.fcr.weather.objects.WeatherImpl
import xyz.fcr.weather.objects.WeatherInterface

class WeatherFragment : Fragment() {
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!
    private val weatherImpl: WeatherInterface = WeatherImpl()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val weatherObj = weatherImpl.getWeatherData()

        binding.buttonUpdate.setOnClickListener {
            binding.textviewCity.text = weatherObj.currentCity
            binding.textviewTemp.text = weatherObj.currentTemp
            binding.textviewHighTemp.text = weatherObj.highTemp
            binding.textviewLowTemp.text = weatherObj.lowTemp
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}