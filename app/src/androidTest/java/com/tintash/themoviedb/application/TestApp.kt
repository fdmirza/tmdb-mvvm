package com.tintash.themoviedb.application

import android.app.Application
import org.koin.core.context.startKoin

class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            modules(emptyList())
        }
    }
}
