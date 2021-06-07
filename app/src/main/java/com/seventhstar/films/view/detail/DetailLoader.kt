package com.seventhstar.films.view.detail

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.viewbinding.BuildConfig
import com.google.gson.Gson
import com.seventhstar.films.model.FilmDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@RequiresApi(Build.VERSION_CODES.N)
class DetailLoader(private val listener: DetailLoaderListener, private val id: Int) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadFilmInfo() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/${id}?api_key=${com.seventhstar.films.BuildConfig.API_KEY}")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
//                    urlConnection.addRequestProperty(
//                        "X-Yandex-API-Key",
//                        BuildConfig.WEATHER_API_KEY
//                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))

                    // преобразование ответа от сервера (JSON) в модель данных (FilmDTO)
                    val filmDTO: FilmDTO =
                        Gson().fromJson(getLines(bufferedReader), FilmDTO::class.java)
                    handler.post { listener.onLoaded(filmDTO) }
                } catch (e: Exception) {
                    Log.e("", "Fail connection", e)
                    e.printStackTrace()
                    listener.onFailed(e)
                } finally {
                    urlConnection.disconnect()
                }
            }).start()
        } catch (e: MalformedURLException) {
            Log.e("", "Fail URI", e)
            e.printStackTrace()
            listener.onFailed(e)
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    interface DetailLoaderListener {
        fun onLoaded(filmDTO: FilmDTO)
        fun onFailed(throwable: Throwable)
    }
}