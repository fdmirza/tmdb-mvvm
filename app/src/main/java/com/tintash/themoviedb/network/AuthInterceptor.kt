package com.tintash.themoviedb.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var req = chain.request()
        // DONT INCLUDE API KEYS IN YOUR SOURCE CODE
        val url = req.url.newBuilder().addQueryParameter("api_key", "a9c08ed5a91190b0d23008386d9b28e1").build()
        req = req.newBuilder().url(url).build()
        return chain.proceed(req)
    }
}