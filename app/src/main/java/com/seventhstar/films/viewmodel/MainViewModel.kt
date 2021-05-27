package com.seventhstar.films.viewmodel

import android.os.Build
import android.os.Handler
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.seventhstar.films.model.FilmsDTO
import com.seventhstar.films.model.Repository
import com.seventhstar.films.model.RepositoryImpl
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getFilmsFromLocalStorage() = getDataFromLocalSource()

    @RequiresApi(Build.VERSION_CODES.N)
    fun getFilmsFromServer() = getDataFromServer()

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getDataFromServer() {
        liveDataToObserve.value = AppState.Loading
        liveDataToObserve.postValue(AppState.Success(repositoryImpl.getFilmsFromServer()))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            liveDataToObserve.postValue(AppState.Success(repositoryImpl.getFilmsFromLocalStorage()))
        }.start()
    }

}