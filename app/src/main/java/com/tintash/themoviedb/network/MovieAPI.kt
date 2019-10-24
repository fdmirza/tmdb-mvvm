package com.tintash.themoviedb.network

import com.tintash.themoviedb.model.GenreResponse
import com.tintash.themoviedb.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAPI
{
    @GET("discover/movie")
    suspend fun getTrendingMovies(
            @Query("page") page:Int,
            @Query("sort_by") sort_by : String = "popularity.desc"
    ): MovieResponse

    @GET("discover/movie")
    suspend fun getTrendingMoviesWithGenre(
            @Query("page") page:Int,
            @Query("with_genres") with_genres:Int,
            @Query("sort_by") sort_by : String = "popularity.desc"

    ): MovieResponse


    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponse
}