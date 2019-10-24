package com.tintash.themoviedb.network

import com.bumptech.glide.Glide
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val imageLoader = module {
    single { ImageLoader(androidApplication()) }
}