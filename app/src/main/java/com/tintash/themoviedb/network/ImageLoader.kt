package com.tintash.themoviedb.network

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions



class ImageLoader(val context: Context) {

    fun loadImageWithImageBase(url: String?, imageView: ImageView, size: PosterSize = PosterSize.W185) {

        val options = RequestOptions().centerCrop()
        Glide.with(context)
                .load("${IMAGE_BASE_URL}${size.value}${url}")
                .apply(options)
                .into(imageView)
    }


}

enum class PosterSize(val value: String) {

    W92("w92"),
    W154("w154"),
    W185("w185"),
    W342("w342"),
    W500("w500"),
    W780("w780"),
    ORIGINAL("original")
}