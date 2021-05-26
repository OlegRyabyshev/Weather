package xyz.fcr.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.cities_fragment.*
import kotlinx.android.synthetic.main.weather_fragment.*
import xyz.fcr.weather.R
import xyz.fcr.weather.databinding.CitiesFragmentBinding
import xyz.fcr.weather.objects.WeatherObj
import xyz.fcr.weather.recyclerview.CitiesAdapter

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

        val exampleList = generateDummyList(40)
        recycler_view_cities.adapter = CitiesAdapter(exampleList)
        //recycler_view_weather.layoutManager = LinearLayoutManager(context)
        recycler_view_cities.setHasFixedSize(true)
    }

    private fun generateDummyList(size: Int): List<WeatherObj> {
        val list = ArrayList<WeatherObj>()
        for (i in 0 until size) {
            val item = WeatherObj("Moscow", "13", "123")
            list += item
        }
        return list
    }


}