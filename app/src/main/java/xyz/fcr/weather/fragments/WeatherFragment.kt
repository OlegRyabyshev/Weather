package xyz.fcr.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.fcr.weather.databinding.WeatherFragmentBinding
import xyz.fcr.weather.objects.WeatherInfo

class WeatherFragment : Fragment() {
    private var _binding: WeatherFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = WeatherFragmentBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val weatherInfo = WeatherInfo()

        binding.buttonUpdate.setOnClickListener {
            binding.textviewTemp.text = weatherInfo.currentTemp.toString()
            binding.textviewPressure.text = weatherInfo.currentPressure.toString()
        }
    }
}