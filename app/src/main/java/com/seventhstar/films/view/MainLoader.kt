package com.seventhstar.films.view

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.seventhstar.films.BuildConfig
import com.seventhstar.films.model.FilmsDTO
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

@RequiresApi(Build.VERSION_CODES.N)
class MainLoader(private val listener: MainLoaderListener) {

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadFilmsList() {
        try {
            val uri =
                URL("https://api.themoviedb.org/3/movie/popular")
            val handler = Handler()
            Thread(Runnable {
                lateinit var urlConnection: HttpsURLConnection
                try {
                    urlConnection = uri.openConnection() as HttpsURLConnection
                    urlConnection.requestMethod = "GET"
                    urlConnection.addRequestProperty(
                        "api_key",
                        BuildConfig.API_KEY
                    )
                    urlConnection.readTimeout = 10000
                    val bufferedReader =
                        BufferedReader(InputStreamReader(urlConnection.inputStream))

                    // преобразование ответа от сервера (JSON) в модель данных (FilmDTO)
                    val result = getLines(bufferedReader)
                    val filmsDTO: FilmsDTO =
                        Gson().fromJson(result, FilmsDTO::class.java)
                    handler.post {
                        listener.onLoaded(filmsDTO)
                    }
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

    interface MainLoaderListener {
        fun onLoaded(filmsDTO: FilmsDTO)
        fun onFailed(throwable: Throwable)
    }
}
