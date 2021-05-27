package com.seventhstar.films.view

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seventhstar.films.R
import com.seventhstar.films.databinding.MainFragmentBinding
import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.model.FilmsDTO
import com.seventhstar.films.view.detail.DetailsFragment
import com.seventhstar.films.viewmodel.AppState
import com.seventhstar.films.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    companion object {
        fun newInstance() = MainFragment()
    }

    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: FilmDTO) {
            val manager = activity?.supportFragmentManager
            if (manager != null) {
                val bundle = Bundle()
                bundle.putParcelable(DetailsFragment.BUNDLE_EXTRA_DTO, film)
                manager.beginTransaction()
                    .replace(R.id.container, DetailsFragment.newInstance(bundle))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    private val onLoadListener: MainLoader.MainLoaderListener =
        object : MainLoader.MainLoaderListener {

            override fun onLoaded(filmsDTO: FilmsDTO) {
                //TODO("Not yet implemented")
                //liveDataToObserve.postValue(AppState.Success(filmsDTO))
                filmsDTO.results?.let { adapter.setDTOData(it) }
            }

            override fun onFailed(throwable: Throwable) {
                //Обработка ошибки
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getLiveData().observe(
            viewLifecycleOwner,
            Observer { renderData(it) }
        )
        //viewModel.getFilmsFromLocalStorage()
        //viewModel.getFilmsFromServer()
        val loader = MainLoader(onLoadListener)
        loader.loadFilmsList()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val filmsData = appState.filmsData
                binding.loadingLayout.visibility = View.GONE
                //adapter.setDTOData(filmsData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
//                Snackbar
//                    .make(binding.mainView, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
//                    .setAction(getString(R.string.reload)) { viewModel.getFilmsFromLocalStorage() }
//                    .show()
            }
        }
    }

    override fun onDestroyView() {
        //adapter.removeListener()
        super.onDestroyView()
        _binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(film: FilmDTO)
    }
}