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
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.fcr.weather.App
import xyz.fcr.weather.R
import xyz.fcr.weather.databinding.CitiesFragmentBinding
import xyz.fcr.weather.datastore.room.convertToCityList
import xyz.fcr.weather.datastore.room.convertToEntity
import xyz.fcr.weather.datastore.room.convertToEntityList
import xyz.fcr.weather.fragments.adapters.CitiesAdapter
import xyz.fcr.weather.objects.City
import xyz.fcr.weather.util.CityList
import java.io.IOException

private const val REQUEST_CODE = 12345
private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f

class CitiesFragment : Fragment() {
    private var _binding: CitiesFragmentBinding? = null
    private val binding get() = _binding!!
    private var buttonClicked: Boolean = false
    private lateinit var listOfCities: MutableList<City>

    private lateinit var citiesRecyclerView: RecyclerView

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

        loadListOfCities()

        citiesRecyclerView = binding.recyclerViewCities

        citiesRecyclerView.adapter = CitiesAdapter(listOfCities, activity)
        citiesRecyclerView.setHasFixedSize(true)

        fabAdd = binding.fabAdd
        fabAddCity = binding.fabAddCity
        fabAddLocation = binding.fabAddLocation
        backButton = binding.backButton

        fabAdd.setOnClickListener {
            onButtonClicked()
        }

        fabAddCity.setOnClickListener {
            showAlertWithCityInput(requireContext())
        }

        fabAddLocation.setOnClickListener {
            checkPermission()
        }

        backButton.setOnClickListener {
            activity?.supportFragmentManager?.popBackStack()
        }

    }

    private fun updateAdapter() {
        citiesRecyclerView.adapter?.notifyDataSetChanged()
        citiesRecyclerView.smoothScrollToPosition(listOfCities.size - 1)
    }

    private fun loadListOfCities() {
        listOfCities = convertToCityList(cityDB.getListOfCities())

        if (listOfCities.isEmpty()) {
            listOfCities = CityList().list.toMutableList()
            cityDB.addListCity(convertToEntityList(listOfCities))
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
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val isGPSEnabled =
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

            if (isGPSEnabled) {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,
                    onLocationListener
                )

            } else {
                showRationaleDialog()
            }
        }
    }

    private val onLocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            context?.let { getAddressAsync(it, location) }
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun getAddressAsync(context: Context, location: Location) {

        val geoCoder = Geocoder(context)
        Thread {
            try {
                val address =
                    geoCoder.getFromLocation(location.latitude, location.longitude, 1)
                val city =
                    City(address[0].adminArea, location.latitude, location.longitude)

                addCityToRepository(city)
                requireActivity().runOnUiThread { updateAdapter() }

            } catch (e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Error. Check your connection.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    private fun addCityToRepository(city: City) {
        cityDB.addCity(convertToEntity(city))
        listOfCities.add(city)
    }

    private fun showAlertWithCityInput(context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Enter your city name")

        val viewInflated: View = LayoutInflater.from(getContext())
            .inflate(R.layout.add_city_alert, view as ViewGroup?, false)

        val input = viewInflated.findViewById<View>(R.id.input) as EditText
        builder.setView(viewInflated)

        builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog.dismiss()
            val cityInput = input.text.toString()
            createNewCityAsync(context, cityInput)
        }

        builder.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog.cancel()
        }

        builder.show()
    }

    private fun createNewCityAsync(context: Context, cityString: String) {
        val geoCoder = Geocoder(context)

        Thread {
            try {
                val address = geoCoder.getFromLocationName(cityString, 1)
                val city = City(cityString, address[0].latitude, address[0].longitude)
                addCityToRepository(city)
                requireActivity().runOnUiThread { updateAdapter() }

            } catch (e: IOException) {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Error. Check your connection.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } catch (e: Exception) {
                requireActivity().runOnUiThread {
                    Toast.makeText(
                        requireContext(),
                        "Can't find this city",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }
}