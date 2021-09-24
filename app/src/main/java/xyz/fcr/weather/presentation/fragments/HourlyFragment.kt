package xyz.fcr.weather.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.fcr.weather.databinding.HourlyFragmentBinding
import xyz.fcr.weather.data.datastore.CitySaver
import xyz.fcr.weather.presentation.fragments.adapters.HourlyAdapter

class HourlyFragment : Fragment() {
    private var _binding: HourlyFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HourlyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val city = CitySaver().getFromSharedPref(requireContext())

        if (city.hourly != null) {
            binding.recyclerViewWeather.adapter = HourlyAdapter(city.hourly!!)
        }
    }
}