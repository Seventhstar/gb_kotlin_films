package com.seventhstar.films.model

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.android.parcel.Parcelize
import java.io.BufferedReader
import java.util.stream.Collectors

@Parcelize
data class Film(
    val id: Int = 0,
    val name: String = "emptyName",
    val imgUrl: String = "no URL",
    val year: Int = 0,
    val rating: Double = 0.0,
    val description: String = "No description"
) : Parcelable

fun getLocalFilms(): List<Film> {
    return listOf(
        Film(550,
            "Lethal Weapon",
            "https://upload.wikimedia.org/wikipedia/ru/thumb/f/f1/Lethal_Weapon_Poster.jpg/201px-Lethal_Weapon_Poster.jpg",
            1987,
            7.6, "good film"
        ),
        Film(
            551,
            "Die Hard",
            "https://upload.wikimedia.org/wikipedia/ru/thumb/f/f6/Die_Hard.gif/211px-Die_Hard.gif",
            1988,
            8.2, "good film"
        ),
        Film(
            552,
            "Sicario",
            "https://lh3.googleusercontent.com/proxy/JNdjrkl4fDOWy7KmzCjSlWE_xb5tsBvPqtK0gbROwABN1ivqjCH24w5J3bJL1U7xKq6a2oFIUKzAqhcJTwzczlUGWc6I3yvPc4XqITobSFSk1YP2jL6UxKefSV_ihgfrcUIKq17MMbmVq9zOcNi-Gq-TQorrzCtNfhigXvXM4EI",
            2015,
            7.6, "good film"
        )
    )
}

@RequiresApi(Build.VERSION_CODES.N)
fun getServerFilms(): List<Film> {

//    try {
//        val uri =
//            URL("https://api.themoviedb.org/3/movie/popular?api_key=")
//        val handler = Handler()
//        Thread(Runnable {
//            lateinit var urlConnection: HttpsURLConnection
//            try {
//                urlConnection = uri.openConnection() as HttpsURLConnection
//                urlConnection.requestMethod = "GET"
////                urlConnection.addRequestProperty(
////                    "X-Yandex-API-Key",
////                    YOUR_API_KEY
////                )
//                urlConnection.readTimeout = 10000
//                val bufferedReader =
//                    BufferedReader(InputStreamReader(urlConnection.inputStream))
//
//                // преобразование ответа от сервера (JSON) в модель данных (WeatherDTO)
//                val filmsDTO: FilmsDTO =
//                    Gson().fromJson(getLines(bufferedReader), FilmsDTO::class.java)
//
//                    handler.post {
//                        //displayWeather(filmsDTO)
//                        filmsDTO
//                    }
//
//            } catch (e: Exception) {
//                Log.e("", "Fail connection", e)
//                e.printStackTrace()
//                //Обработка ошибки
//            } finally {
//                urlConnection.disconnect()
//            }
//        }).start()
//    } catch (e: MalformedURLException) {
//        Log.e("", "Fail URI", e)
//        e.printStackTrace()
//        //Обработка ошибки
//    }
//    try {
//        val uri =
//            URL("https://api.themoviedb.org/3/movie/popular?api_key=4f01abb3f823bd373049c003aac3f23c")
//        val handler = Handler()
//        Thread(Runnable {
//            lateinit var urlConnection: HttpsURLConnection
//            try {
//                urlConnection = uri.openConnection() as HttpsURLConnection
//                urlConnection.requestMethod = "GET"
////                urlConnection.addRequestProperty(
////                    "X-Yandex-API-Key",
////                    YOUR_API_KEY
////                )
//                urlConnection.readTimeout = 10000
//                val bufferedReader =
//                    BufferedReader(InputStreamReader(urlConnection.inputStream))
//
//                // преобразование ответа от сервера (JSON) в модель данных (WeatherDTO)
//                val filmsDTO: FilmsDTO =
//                    Gson().fromJson(getLines(bufferedReader), FilmsDTO::class.java)
//                    handler.post { displayWeather(filmsDTO) }
//            } catch (e: Exception) {
//                Log.e("", "Fail connection", e)
//                e.printStackTrace()
//                //Обработка ошибки
//            } finally {
//                urlConnection.disconnect()
//            }
//        }).start()
//    } catch (e: MalformedURLException) {
//        Log.e("", "Fail URI", e)
//        e.printStackTrace()
//        //Обработка ошибки
//    }


    return listOf(
        Film(
            550,
            "Sicario",
            "https://lh3.googleusercontent.com/proxy/JNdjrkl4fDOWy7KmzCjSlWE_xb5tsBvPqtK0gbROwABN1ivqjCH24w5J3bJL1U7xKq6a2oFIUKzAqhcJTwzczlUGWc6I3yvPc4XqITobSFSk1YP2jL6UxKefSV_ihgfrcUIKq17MMbmVq9zOcNi-Gq-TQorrzCtNfhigXvXM4EI",
            2015,
            7.6, "good film"
        )
    )
}

@RequiresApi(Build.VERSION_CODES.N)
private fun getLines(reader: BufferedReader): String {
    return reader.lines().collect(Collectors.joining("\n"))
}