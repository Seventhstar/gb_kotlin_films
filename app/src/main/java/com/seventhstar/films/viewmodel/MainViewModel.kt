package com.seventhstar.films.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seventhstar.films.app.App
import com.seventhstar.films.app.AppState
import com.seventhstar.films.model.FilmFilter
import com.seventhstar.films.model.FilmsDTO
import com.seventhstar.films.repository.LocalRepositoryImpl
import com.seventhstar.films.repository.MainRepository
import com.seventhstar.films.repository.MainRepositoryImpl
import com.seventhstar.films.repository.RemoteDataSource
import com.seventhstar.films.utils.convertDtoToModel
import com.seventhstar.films.utils.convertEntityToModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val liveDataFilter: MutableLiveData<FilmFilter> = MutableLiveData(),
    private val mainRepositoryImpl: MainRepository = MainRepositoryImpl(RemoteDataSource()),
    private val favoritesRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(App.getFavoritesDao())
) : ViewModel() {

    fun getLiveData() = liveDataToObserve
    fun getFilter() = liveDataFilter

    fun getFilmsFromLocalStorage() = getDataFromLocalSource()

    fun getFilmsFromRemoteSource() {
        liveDataToObserve.value = AppState.Loading
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

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(1000)
            val favorites = favoritesRepositoryImpl.getAllFavorites()
            liveDataToObserve.postValue(AppState.Success(convertEntityToModel(favorites)))
        }.start()
    }

    fun setFilter(query: String?) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            Thread.sleep(300)
            liveDataToObserve.postValue(AppState.SetFilter(FilmFilter(query!!)))
        }.start()
    }

}