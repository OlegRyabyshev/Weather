package xyz.fcr.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import xyz.fcr.weather.databinding.CitiesFragmentBinding

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


}