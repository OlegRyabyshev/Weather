package xyz.fcr.weather.presentation

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import xyz.fcr.weather.R
import xyz.fcr.weather.presentation.broadcast.ConnectionBroadcastReceiver
import xyz.fcr.weather.presentation.fragments.WeatherFragment

class MainActivity : AppCompatActivity() {

    private val receiver = ConnectionBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, WeatherFragment())
                .commit()
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(receiver, intentFilter)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }
}