package com.tintash.themoviedb.viewmodel

import androidx.lifecycle.ViewModel
import com.tintash.themoviedb.model.Movie

class DetailViewModel : ViewModel() {

    lateinit var movie: Movie

    fun setMovieItem(item: Movie?) {

     if(item !=null)
         movie = item
    }

    fun getUrl() = movie.backdrop_path


}