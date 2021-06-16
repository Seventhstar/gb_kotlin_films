package com.seventhstar.films.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seventhstar.films.app.App.Companion.getFavoritesDao
import com.seventhstar.films.app.AppState
import com.seventhstar.films.model.Film
import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.repository.DetailsRepository
import com.seventhstar.films.repository.DetailsRepositoryImpl
import com.seventhstar.films.repository.LocalRepositoryImpl
import com.seventhstar.films.repository.RemoteDataSource
import retrofit2.Call
import retrofit2.Response

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"
private const val CORRUPTED_DATA = "Неполные данные"

class DetailsViewModel(
    val detailsLiveData: MutableLiveData<AppState> = MutableLiveData(),
    private val detailsRepositoryImpl: DetailsRepository = DetailsRepositoryImpl(RemoteDataSource()),
    private val favoritesRepositoryImpl: LocalRepositoryImpl = LocalRepositoryImpl(getFavoritesDao())
) : ViewModel() {

    fun getLiveData() = detailsLiveData

    //@RequiresApi(Build.VERSION_CODES.N)
    fun getDataFromServer(id: Int) {
        detailsLiveData.value = AppState.Loading
        detailsRepositoryImpl.getDetailsFromServer(id, callback)
    }

    fun saveFavoriteToDB(film: Film) {
        favoritesRepositoryImpl.saveEntity(film)
    }

    fun isFavorite(id: Int): Boolean {
        return favoritesRepositoryImpl.isFavorite(id)
    }

    private val callback = object :
        retrofit2.Callback<FilmDTO> {

        override fun onResponse(call: Call<FilmDTO>, response: Response<FilmDTO>) {
            val serverResponse: FilmDTO? = response.body()
            detailsLiveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<FilmDTO>, t: Throwable) {
            detailsLiveData.postValue(AppState.Error(Throwable(t.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: FilmDTO): AppState {
            //val film = serverResponse
            return if (serverResponse?.id == null) {
                AppState.Error(Throwable(CORRUPTED_DATA))
            } else {
                //AppState.Success(convertDtoToModel(serverResponse))
                AppState.Loaded(serverResponse)
            }
        }
    }


}