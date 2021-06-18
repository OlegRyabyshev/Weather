package xyz.fcr.weather.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

import xyz.fcr.weather.R
import xyz.fcr.weather.databinding.MapsMainFragmentBinding
import xyz.fcr.weather.datastore.CitySaver

class MapsFragment : Fragment() {
    private var _binding: MapsMainFragmentBinding? = null
    private val binding get() = _binding!!

    private val callback = OnMapReadyCallback { googleMap ->
        val city = CitySaver().getFromSharedPref(requireContext())
        val cityLatLng = LatLng(city.lat, city.lon)

        googleMap.addMarker(MarkerOptions().position(cityLatLng).title("Marker in ${city.name}"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(cityLatLng))
        googleMap.setMinZoomPreference(9F)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MapsMainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val city = CitySaver().getFromSharedPref(requireContext())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.mapCityName.text = city.name

        binding.backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }
    }
}