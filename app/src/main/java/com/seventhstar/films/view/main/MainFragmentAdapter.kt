package com.seventhstar.films.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seventhstar.films.R
import com.seventhstar.films.model.Film
import com.seventhstar.films.view.main.MainFragment.OnItemViewClickListener
import com.squareup.picasso.Picasso

class MainFragmentAdapter(private var onItemViewClickListener: OnItemViewClickListener?) :
    RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var filmsData: List<Film> = listOf()

    fun setData(data: List<Film>) {
        filmsData = data
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
        holder.bind(filmsData[position])
    }

    override fun getItemCount(): Int {
        return filmsData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(film: Film) {
            itemView.findViewById<TextView>(R.id.tv_film_name).text = film.name
            itemView.findViewById<TextView>(R.id.tv_film_year).text = film.year.toString()
            itemView.findViewById<TextView>(R.id.tv_film_rating).text = film.rating.toString()

            val poster = itemView.findViewById<ImageView>(R.id.movie_poster)

            Picasso.get()
                .load(film.imgUrl)
                .into(poster);

            itemView.setOnClickListener()
            {
                onItemViewClickListener?.onItemViewClick(film)
            }
        }
    }
}