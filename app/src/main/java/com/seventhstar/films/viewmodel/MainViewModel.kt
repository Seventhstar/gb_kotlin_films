package com.seventhstar.films.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seventhstar.films.app.App
import com.seventhstar.films.app.AppState
import com.seventhstar.films.model.FilmsDTO
import com.seventhstar.films.model.Repository
import com.seventhstar.films.model.RepositoryImpl
import com.seventhstar.films.repository.LocalRepositoryImpl
import com.seventhstar.films.repository.MainRepository
import com.seventhstar.films.repository.MainRepositoryImpl
import com.seventhstar.films.repository.RemoteDataSource
import com.seventhstar.films.utils.convertDtoToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.BufferedReader
import java.util.stream.Collectors

private const val SERVER_ERROR = "Ошибка сервера"

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val mainRepositoryImpl: MainRepository = MainRepositoryImpl(RemoteDataSource()),
    private val favoritesRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(App.getFavoritesDao())
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getFilmsFromLocalStorage() = getDataFromLocalSource()

    fun getFilmsFromRemoteSource() {
        liveDataToObserve.value = AppState.Loading
        val favorites = favoritesRepositoryImpl.getAllFavorites()

        mainRepositoryImpl.getFilmsFromServer(callback)
    }

    private val callback = object : Callback<FilmsDTO> {
        override fun onResponse(call: Call<FilmsDTO>, response: Response<FilmsDTO>) {

            val serverResponse: FilmsDTO? = response.body()
            liveDataToObserve.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    AppState.Success(convertDtoToModel(serverResponse.results!!))
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<FilmsDTO>, t: Throwable) {
            TODO("Not yet implemented")
        }

    }

//    @RequiresApi(Build.VERSION_CODES.N)
//    fun getFilmsFromServer() = getDataFromServer()

//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun getDataFromServer() {
//        liveDataToObserve.value = AppState.Loading
//        liveDataToObserve.postValue(AppState.Success(repositoryImpl.getFilmsFromServer()))
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun getLines(reader: BufferedReader): String {
//        return reader.lines().collect(Collectors.joining("\n"))
//    }

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
           // liveDataToObserve.postValue(AppState.Success(favoritesRepositoryImpl.getFilmsFromLocalStorage()))
        }.start()
    }

}