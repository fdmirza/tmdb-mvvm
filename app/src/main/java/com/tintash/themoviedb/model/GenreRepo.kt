package com.tintash.themoviedb.model

import com.tintash.themoviedb.network.MovieAPI
import org.koin.dsl.module


val genresModule = module {
    factory { GenreRepo(get()) }
}

class GenreRepo(private val moviesApi: MovieAPI){

    suspend fun getGenres() = moviesApi.getGenres().genres
}