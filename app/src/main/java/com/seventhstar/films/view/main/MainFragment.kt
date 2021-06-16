package com.seventhstar.films.view.main

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.seventhstar.films.R
import com.seventhstar.films.app.AppState
import com.seventhstar.films.databinding.MainFragmentBinding
import com.seventhstar.films.model.Film
import com.seventhstar.films.view.detail.DetailsFragment
import com.seventhstar.films.viewmodel.MainViewModel

class MainFragment : Fragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    private var sharedPreferences: SharedPreferences? = null

    companion object {
        fun newInstance() = MainFragment()
    }

    private val adapter = MainFragmentAdapter(object : OnItemViewClickListener {
        override fun onItemViewClick(film: Film) {
            activity?.supportFragmentManager?.apply {
                beginTransaction()
                    .replace(
                        R.id.nav_host_fragment, //R.id.container
                        DetailsFragment.newInstance(Bundle().apply {
                            putParcelable(DetailsFragment.BUNDLE_EXTRA_DTO, film)
                        })
                    )
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = MainFragmentBinding.inflate(inflater, container, false)
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.container, MainFragment.newInstance())
//            .commitNow()

        return binding.root
    }


    //@RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = adapter

//        if (findNavController().currentDestination?.id ) {
//            Toast.makeText(context, "ID is home", Toast.LENGTH_SHORT).show()
//        }

        viewModel.getLiveData().observe(
            viewLifecycleOwner,
            Observer { renderData(it) }
        )

        if (arguments?.get("Popular") as Boolean) {
            viewModel.getFilmsFromRemoteSource()
        } else {
            viewModel.getFilmsFromLocalStorage()
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {

                sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                val showOnlyBest = sharedPreferences!!.getBoolean("showOnlyBest", false)

                var filmsData = appState.filmsData
                if (showOnlyBest) filmsData = filmsData.filter { f -> f.rating > 8 }

                binding.loadingLayout.visibility = View.GONE
                adapter.setData(filmsData)
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