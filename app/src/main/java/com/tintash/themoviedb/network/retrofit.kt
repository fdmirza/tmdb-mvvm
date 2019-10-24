package com.tintash.themoviedb.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { AuthInterceptor() }
    factory { HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    } }
    factory { getOkHttpClient(get(),get()) }
    factory { getMovieApi(get()) }
    single { retrofit(get()) }
}

fun retrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create()).build()
}

fun getOkHttpClient(authInterceptor: AuthInterceptor,loggingInterceptor: HttpLoggingInterceptor) =
        OkHttpClient().newBuilder()
                .addInterceptor(authInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()

fun getMovieApi(retrofit: Retrofit) = retrofit.create(MovieAPI::class.java)
