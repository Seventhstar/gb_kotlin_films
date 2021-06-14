package com.seventhstar.films.view.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seventhstar.films.app.AppState
import com.seventhstar.films.databinding.DetailsFragmentBinding
import com.seventhstar.films.model.Film
import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.viewmodel.DetailsViewModel
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: Film
    private val viewModel: DetailsViewModel by lazy { ViewModelProvider(this).get(DetailsViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable(BUNDLE_EXTRA_DTO) ?: Film()

        binding.btnMakeFavorite.setOnClickListener {
            viewModel.saveFavoriteToDB(filmBundle)
        }
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer { renderData(it) })
        viewModel.getDataFromServer(filmBundle.id)
    }

    private fun renderData(appState: AppState) {

        when (appState) {
            is AppState.Loaded -> {
                displayFilmInfo(appState.filmInfo)
            }
            is AppState.Loading -> {
                //loadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                //loadingLayout.visibility = View.GONE
//                Snackbar
//                    .make(binding.mainView, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
//                    .setAction(getString(R.string.reload)) { viewModel.getFilmsFromLocalStorage() }
//                    .show()
            }
        }
    }


    private fun displayFilmInfo(filmInfo: FilmDTO) {
        with(binding) {
            tvDetailsFilmName.text = filmInfo.title
            tvFilmDescription.text = filmInfo.overview
            tvDetailFilmYear.text = filmInfo.vote_average.toString()

            Picasso.get()
                .load(filmInfo.getPosterURL())
                .into(detailFilmPoster);
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val BUNDLE_EXTRA = "film"
        const val BUNDLE_EXTRA_DTO = "filmDTO"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}
