package xyz.fcr.weather.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.fcr.weather.App
import xyz.fcr.weather.R
import xyz.fcr.weather.databinding.CitiesFragmentBinding
import xyz.fcr.weather.datastore.room.convertToCity
import xyz.fcr.weather.datastore.room.convertToCityList
import xyz.fcr.weather.datastore.room.convertToEntity
import xyz.fcr.weather.datastore.room.convertToEntityList
import xyz.fcr.weather.fragments.adapters.CitiesAdapter
import xyz.fcr.weather.objects.City
import xyz.fcr.weather.objects.CityList
import java.io.IOException

private const val REQUEST_CODE = 12345
private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f

class CitiesFragment : Fragment() {
    private var _binding: CitiesFragmentBinding? = null
    private val binding get() = _binding!!
    private var buttonClicked: Boolean = false

    private val cityDB = App.getHistoryDao()

    private lateinit var fabAdd: FloatingActionButton
    private lateinit var fabAddCity: ExtendedFloatingActionButton
    private lateinit var fabAddLocation: ExtendedFloatingActionButton
    private lateinit var backButton: ImageView

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_open_anim)
    }

    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_close_anim)
    }

    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim)
    }

    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim)
    }

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

        binding.recyclerViewCities.adapter = CitiesAdapter(loadListOfCities(), activity)
        binding.recyclerViewCities.setHasFixedSize(true)

        fabAdd = binding.fabAdd
        fabAddCity = binding.fabAddCity
        fabAddLocation = binding.fabAddLocation
        backButton = binding.backButton

        fabAdd.setOnClickListener {
            onButtonClicked()
        }

        fabAddCity.setOnClickListener {
            Toast.makeText(context, "In development", Toast.LENGTH_SHORT).show()
        }

        fabAddLocation.setOnClickListener {
            checkPermission()
        }

        backButton.setOnClickListener {
            val manager = activity?.supportFragmentManager
            manager?.popBackStack()
        }

    }

    private fun onButtonClicked() {

        //Visibility
        if (!buttonClicked) {
            fabAddLocation.visibility = View.VISIBLE
            fabAddCity.visibility = View.VISIBLE
        } else {
            fabAddLocation.visibility = View.INVISIBLE
            fabAddCity.visibility = View.INVISIBLE
        }

        //Animations
        if (!buttonClicked) {
            fabAdd.startAnimation(rotateOpen)
            fabAddCity.startAnimation(fromBottom)
            fabAddLocation.startAnimation(fromBottom)
        } else {
            fabAdd.startAnimation(rotateClose)
            fabAddCity.startAnimation(toBottom)
            fabAddLocation.startAnimation(toBottom)
        }

        //Clickable
        if (!buttonClicked) {
            fabAddCity.isClickable = true
            fabAddLocation.isClickable = true
        } else {
            fabAddCity.isClickable = false
            fabAddLocation.isClickable = false
        }

        buttonClicked = !buttonClicked
    }

    private fun loadListOfCities(): List<City> {
        var list = convertToCityList(cityDB.getListOfCities())

        if (list.isEmpty()) {
            cityDB.addListCity(convertToEntityList(CityList().list))
            list = convertToCityList(cityDB.getListOfCities())
        }

        return list
    }

    private fun checkPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
                    == PackageManager.PERMISSION_GRANTED -> {
                getLocation()
                return
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                showRationaleDialog()
            }
            else -> {
                requestPermission()
            }
        }
    }

    private fun requestPermission() {
        requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

    private fun showRationaleDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_rationale_title))
            .setMessage(getString(R.string.dialog_rationale_meaasge))
            .setPositiveButton(getString(R.string.dialog_rationale_give_access)) { _, _ ->
                requestPermission()
            }
            .setNegativeButton(getString(R.string.dialog_rationale_decline)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun getLocation() {

        if (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            var isGPSEnabled = false
            var isNetworkEnabled = false

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,

                )

                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                if (location != null) {
                    latitude = location.getLatitude()
                    longitude = location.getLongitude()
                }
            } else if (isNetworkEnabled)
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    this
                )
            Log.d(TAG, "GPS Enabled")
            if (locationManager != null) {
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                if (location != null) {
                    latitude = location.getLatitude()
                    longitude = location.getLongitude()
                }
            }
        }
    }

    fun getLocatioan(): Location? {
        try {
            locationManager = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


            this.canGetLocation = true
            if (isNetworkEnabled) {
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    this
                )
                Log.d(TAG, "Network")
                if (locationManager != null) {
                    location =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (location != null) {
                        latitude = location.getLatitude()
                        longitude = location.getLongitude()
                    }
                }
            }
            // if GPS Enabled get lat/long using GPS Services
            if (isGPSEnabled && location == null) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES,
                    this
                )
                Log.d(TAG, "GPS Enabled")
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (location != null) {
                        latitude = location.getLatitude()
                        longitude = location.getLongitude()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Location Not Found")
        }
        return location
    }

    private val onLocationListener = object : LocationListener {

        override fun onLocationChanged(location: Location) {
            context?.let {
                getAddressAsync(it, location)
            }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun getAddressAsync(
        context: Context,
        location: Location
    ) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                val city = City(addresses[0].getAddressLine(0), location.latitude, location.longitude)
                cityDB.addCity(convertToEntity(city))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }
}