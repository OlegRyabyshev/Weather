package xyz.fcr.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.cities_fragment.*
import xyz.fcr.weather.databinding.CitiesFragmentBinding
import xyz.fcr.weather.objects.City
import xyz.fcr.weather.objects.CityList

class CitiesFragment : Fragment() {
    private var _binding: CitiesFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CitiesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view_cities.adapter = CitiesAdapter(CityList().list)
        //recycler_view_weather.layoutManager = LinearLayoutManager(context)
        recycler_view_cities.setHasFixedSize(true)
    }
}