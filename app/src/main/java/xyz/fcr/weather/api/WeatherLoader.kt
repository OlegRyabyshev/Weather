package xyz.fcr.weather.api

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import xyz.fcr.weather.api.dto.WeatherDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val API_KEY: String = "a73c646cde5c5a1ec0adc33aebba434f"

class WeatherLoader {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadWeather(lat: Double, lon: Double, weatherLiveData: WeatherLiveData) {
        var weatherDTO: WeatherDTO? = null

        try {
            val uri = URL(
                "https://api.openweathermap.org/data/2.5/onecall?" +
                        "lat=${lat}&lon=${lon}" +
                        "&appid=${API_KEY}" +
                        "&units=metric"
            )

            Thread {
                var connection: HttpsURLConnection? = null

                try {
                    connection = uri.openConnection() as HttpsURLConnection
                    connection.requestMethod = "GET"
                    connection.readTimeout = 10000

                    val bufferedReader = BufferedReader(InputStreamReader(connection.inputStream))
                    val json = getLines(bufferedReader)

                    weatherDTO = Gson().fromJson(json, WeatherDTO::class.java)

                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                } finally {
                    connection?.disconnect()
                    weatherLiveData.postValue(weatherDTO)
                }
            }.start()

        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}