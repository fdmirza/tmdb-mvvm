package com.tintash.themoviedb.application

import android.app.Application
import com.tintash.themoviedb.model.genresModule
import com.tintash.themoviedb.model.trendingMoviesModule
import com.tintash.themoviedb.network.imageLoader
import com.tintash.themoviedb.network.networkModule
import com.tintash.themoviedb.viewmodel.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MoviesDBApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MoviesDBApplication)
            modules(listOf(networkModule, viewModelModule, trendingMoviesModule, genresModule,imageLoader))
        }
    }
}