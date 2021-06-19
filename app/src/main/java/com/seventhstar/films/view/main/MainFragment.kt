package com.seventhstar.films.view.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.seventhstar.films.R
import com.seventhstar.films.app.AppState
import com.seventhstar.films.databinding.MainFragmentBinding
import com.seventhstar.films.model.Film
import com.seventhstar.films.model.FilmFilter
import com.seventhstar.films.view.detail.DetailsFragment
import com.seventhstar.films.viewmodel.MainViewModel


class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    //private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val viewModel: MainViewModel by lazy { ViewModelProvider(requireActivity()).get(MainViewModel::class.java) }

    private var sharedPreferences: SharedPreferences? = null

    lateinit var filmsData: List<Film>

    companion object {
        fun newInstance() = MainFragment()
    }

    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            val bundle = bundleOf(DetailsFragment.BUNDLE_EXTRA_DTO to film)
            findNavController().navigate(R.id.action_navigation_popular_to_detail, bundle)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

        viewModel.getLiveData().observe(
            viewLifecycleOwner,
            Observer { renderData(it) }
        )

        viewModel.getFilter().observe(
            viewLifecycleOwner,
            Observer { updateFilter(it) }
        )


        if (arguments?.get("Popular") as Boolean) {
            viewModel.getFilmsFromRemoteSource()
        } else {
            viewModel.getFilmsFromLocalStorage()
        }
    }

    private fun updateFilter(filter: FilmFilter) {
        val filteredData =
            filmsData.filter { film: Film ->
                film.name.contains(filter.getName(), true)
            }
        adapter.setData(filteredData)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                val showOnlyBest = sharedPreferences!!.getBoolean("showOnlyBest", false)

                filmsData = appState.filmsData
                if (showOnlyBest) filmsData = filmsData.filter { f -> f.rating > 8 }

                binding.loadingLayout.visibility = View.GONE
                adapter.setData(filmsData)
            }
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
            }
            is AppState.SetFilter -> {
                val filteredData =
                    filmsData.filter { film: Film ->
                        film.name.contains(appState.filter.getName(), true)
                    }

                binding.loadingLayout.visibility = View.GONE
                adapter.setData(filteredData)
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
//                Snackbar
//                    .make(binding.mainView, getString(R.string.error), Snackbar.LENGTH_INDEFINITE)
//                    .setAction(getString(R.string.reload)) { viewModel.getFilmsFromLocalStorage() }
//                    .show()
            }
            else -> {
            }
        }
    }

    override fun onDestroyView() {
        //adapter.removeListener()
        super.onDestroyView()
        _binding = null
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(film: Film)
    }
}