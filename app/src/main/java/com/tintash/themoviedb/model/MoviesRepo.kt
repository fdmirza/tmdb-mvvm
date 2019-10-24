package com.tintash.themoviedb.model

import com.tintash.themoviedb.network.MovieAPI
import org.koin.dsl.module

val trendingMoviesModule = module {
 factory { MoviesRepo(get()) }
}

class MoviesRepo(private val moviesApi:MovieAPI){

    suspend fun getTrendingMovies(pageNumber:Int) = moviesApi.getTrendingMovies(pageNumber).results

    suspend fun getTrendingMoviesWithGenre(pageNumber:Int,genreId:Int) = moviesApi.getTrendingMoviesWithGenre(pageNumber,genreId).results
}