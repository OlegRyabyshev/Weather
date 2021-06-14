package xyz.fcr.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import xyz.fcr.weather.App
import xyz.fcr.weather.R
import xyz.fcr.weather.databinding.CitiesFragmentBinding
import xyz.fcr.weather.datastore.room.convertToCityList
import xyz.fcr.weather.datastore.room.convertToEntityList
import xyz.fcr.weather.fragments.adapters.CitiesAdapter
import xyz.fcr.weather.objects.City
import xyz.fcr.weather.objects.CityList

class CitiesFragment : Fragment() {
    private var _binding: CitiesFragmentBinding? = null
    private val binding get() = _binding!!
    private var buttonClicked: Boolean = false

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
            //checkPermission()
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
        val cityDB = App.getHistoryDao()
        var list = convertToCityList(cityDB.getListOfCities())

        if (list.isEmpty()) {
            cityDB.addListCity(convertToEntityList(CityList().list))
            list = convertToCityList(cityDB.getListOfCities())
        }

        return list
    }
}