package xyz.fcr.weather.presentation.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.widget.Toast

class ConnectionBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        if (ConnectivityManager.EXTRA_NO_CONNECTIVITY == intent?.action) {
            Toast.makeText(context,"Change in connection", Toast.LENGTH_SHORT).show()
        }

        if (ConnectivityManager.EXTRA_NETWORK == intent?.action) {
            Toast.makeText(context,"Connection...", Toast.LENGTH_SHORT).show()
        }
    }
}