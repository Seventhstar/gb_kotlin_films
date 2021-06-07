package com.seventhstar.films.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seventhstar.films.R
import com.seventhstar.films.model.FilmDTO
import com.seventhstar.films.model.FilmsDTO
import com.seventhstar.films.view.MainFragment.OnItemViewClickListener

class MainFragmentAdapter(private var onItemViewClickListener: OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    //private var filmsData: List<Film> = listOf()
    private var filmsDTOData: List<FilmDTO> = listOf()

//    fun setData(data: List<Film>) {
//      //  filmsData = data
//        notifyDataSetChanged()
//    }

    fun setDTOData(data: List<FilmDTO>) {
        filmsDTOData = data
        notifyDataSetChanged()
    }


    fun removeListener() {
        onItemViewClickListener = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_film_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(filmsDTOData[position])
    }

    override fun getItemCount(): Int {
        //return filmsData.size
        return filmsDTOData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(film: FilmDTO) {
            itemView.findViewById<TextView>(R.id.tv_film_name).text = film.title
            itemView.findViewById<TextView>(R.id.tv_film_year).text = film.vote_average.toString()
            itemView.findViewById<TextView>(R.id.tv_film_rating).text = film.vote_average.toString()
            itemView.findViewById<ImageView>(R.id.movie_poster).setImageResource(R.drawable.movie)

            itemView.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(film)
            }
        }
    }
}