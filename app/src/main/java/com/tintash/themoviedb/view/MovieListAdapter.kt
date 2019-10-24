package com.tintash.themoviedb.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.tintash.themoviedb.R
import com.tintash.themoviedb.model.Movie
import com.tintash.themoviedb.network.ImageLoader
import com.tintash.themoviedb.utils.setRating
import kotlinx.android.synthetic.main.row_movie.view.*
import kotlin.math.roundToInt

class MovieListAdapter(val interaction: Interaction,val movies :ArrayList<Movie>,val imageLoader: ImageLoader) :  RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>() {


    override fun getItemCount() = movies.size


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= MovieViewHolder (
        LayoutInflater.from(parent.context)
                .inflate(R.layout.row_movie,parent,false),interaction)


    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }




    inner class MovieViewHolder( itemView: View,val interaction:Interaction) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val clicked = movies[adapterPosition]
            interaction.movieItemClicked(clicked)
        }

        fun bind(item:Movie) = with(itemView){
            tvTitle.text = item.title
            tvRating.setRating(item.vote_average ?:0f)
            imageLoader.loadImageWithImageBase(item.poster_path,ivPoster)
        }

    }

    interface Interaction {
        fun movieItemClicked(model: Movie)
    }


}