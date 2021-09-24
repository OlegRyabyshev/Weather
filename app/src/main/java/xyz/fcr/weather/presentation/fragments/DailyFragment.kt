package xyz.fcr.weather.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.fcr.weather.databinding.DailyFragmentBinding
import xyz.fcr.weather.data.datastore.CitySaver
import xyz.fcr.weather.presentation.fragments.adapters.DailyAdapter

class DailyFragment : Fragment() {
    private var _binding: DailyFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DailyFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val city = CitySaver().getFromSharedPref(requireContext())

        if (city.daily != null) {
            binding.recyclerViewWeather.adapter = DailyAdapter(city.daily!!)
        }

    }
}