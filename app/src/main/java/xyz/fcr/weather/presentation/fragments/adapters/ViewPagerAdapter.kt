package xyz.fcr.weather.presentation.fragments.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import xyz.fcr.weather.presentation.fragments.HourlyFragment
import xyz.fcr.weather.presentation.fragments.DailyFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position){
            1 -> return DailyFragment()
        }

        return HourlyFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}