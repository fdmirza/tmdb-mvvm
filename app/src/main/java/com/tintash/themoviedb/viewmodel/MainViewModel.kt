package com.tintash.themoviedb.viewmodel

import android.os.Bundle
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tintash.themoviedb.model.Genre
import com.tintash.themoviedb.model.GenreRepo
import com.tintash.themoviedb.model.Movie
import com.tintash.themoviedb.model.MoviesRepo
import com.tintash.themoviedb.utils.Event
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class MainViewModel(private val moviesRepo: MoviesRepo, private val genreRepo: GenreRepo) : ViewModel() {



    val moviesList = ArrayList<Movie>()
    val displayList = ArrayList<Movie>()
    var genresList: List<Genre>? = null

    /**
     * trigger to update about genre fetched
     */
    val genresFetched = MutableLiveData<Boolean>(false)

    /**
     * trigger to notify adapter for dataset changed/updated
     */
    val moviesFetched = MutableLiveData<Boolean>(false)

    /**
     * trigger to show genre selection dialog
     */
    val typeSelection = MutableLiveData<Event<String>>()

    /**
     * trigger to show date picker
     */
    val dateSelection = MutableLiveData<Event<String>>()

    var selectedGenreId = -1

    var showProgress = ObservableBoolean(false)

    /**
     * for controlling regressive calls
     */
    var isLoading = false

    /**
     * trigger error to view
     */
    var error = MutableLiveData<String>()

    var pageNumber = 1

    val calendar = Calendar.getInstance()

    /**
     * fetchTrendingMovies from server
     * check if selectedGenreId is not set, use getTrendingMovies() else use getTrendingMoviesWithGenre()
     *
     * On success, increment pagenumber
     * clear display list
     * set movielist
     * set displaylist
     *
     */
    fun fetchTrendingMovies() {
        if (isLoading) return
        isLoading = true
        viewModelScope.launch {
            try {
                showProgress.set(true)
                val response = if (selectedGenreId == -1) moviesRepo.getTrendingMovies(pageNumber)
                else moviesRepo.getTrendingMoviesWithGenre(pageNumber, selectedGenreId)

                showProgress.set(false)
                if(response != null) {
                    pageNumber++
                    moviesList.addAll(response)
                    displayList.clear()
                    displayList.addAll(moviesList)
                    moviesFetched.postValue(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showProgress.set(false)
                error.postValue(e.message)
            }

            isLoading = false
        }
    }

    /**
     * fetch genres list from server
     * set local list
     * trigger UI
     */

    fun fetchGenres() {
        viewModelScope.launch {
            try {
                val list = genreRepo.getGenres()
                if (list != null) {
                    genresList = list
                    genresFetched.postValue(true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                error.postValue(e.message)
            }
        }
    }

    /**
     * handle button click on movie type selection
     */
    fun selectMovieType() {
        typeSelection.value = Event("show type dialog")
    }

    /**
     * handle button click to open date picker
     */
    fun selectDateFilter() {
        dateSelection.value = Event("show data dialog")
    }

    /**
     * genre selected
     * reset page number
     * update selectedGenreId
     * clear movie list
     * fetch trending movies for selected genre
     */
    fun genreSelected(genre: Genre?) {
        pageNumber = 1
        selectedGenreId = genre?.id ?: -1
        moviesList.clear()
        fetchTrendingMovies()
    }

    /**
     * filter movie list on date
     * clear display list
     * set display list with filtered data
     */
    fun filterByDate(year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = sdf.format(calendar.time)
        val filtered = moviesList.filter {
            it.release_date == date
        }

        displayList.clear()
        displayList.addAll(filtered)
        moviesFetched.postValue(true)
    }

}


