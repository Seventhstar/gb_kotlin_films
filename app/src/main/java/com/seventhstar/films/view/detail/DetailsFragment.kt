package com.seventhstar.films.view.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.seventhstar.films.databinding.DetailsFragmentBinding
import com.seventhstar.films.model.FilmDTO
import com.squareup.picasso.Picasso

class DetailsFragment : Fragment() {

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmBundle: FilmDTO

    private val onLoadListener: DetailLoader.DetailLoaderListener =
        object : DetailLoader.DetailLoaderListener {

            override fun onLoaded(filmDTO: FilmDTO) {
                displayFilmInfo(filmDTO)
            }

            override fun onFailed(throwable: Throwable) {
                //Обработка ошибки
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        return binding.getRoot()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filmBundle = arguments?.getParcelable(BUNDLE_EXTRA_DTO) ?: FilmDTO()

        // TODO show load spinner
        //.visibility = View.VISIBLE
        val loader = filmBundle.id?.let { DetailLoader(onLoadListener, it) }
        loader?.loadFilmInfo()
    }

    private fun displayFilmInfo(filmDTO: FilmDTO) {
        with(binding) {
            tvDetailsFilmName.text = filmDTO.title
            tvFilmDescription.text = filmDTO.overview
            tvDetailFilmYear.text = filmDTO.vote_average.toString()

            Picasso.get()
                .load(filmDTO.getPosterURL())
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
