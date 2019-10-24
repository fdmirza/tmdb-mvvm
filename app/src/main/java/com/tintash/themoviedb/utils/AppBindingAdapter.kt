package com.tintash.themoviedb.utils

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.tintash.themoviedb.network.ImageLoader
import com.tintash.themoviedb.network.PosterSize


@BindingAdapter("image_url")
     fun ImageView.setImageUrl( url: String) {
        ImageLoader(context).loadImageWithImageBase(url,this,PosterSize.W500)
    }

@BindingAdapter("release_date")
fun TextView.setReleaseDate(date:String){
    val formatted = "Release date: $date"
    text = formatted
}

@BindingAdapter("rating")
fun TextView.setRating(rating:Float){
    val formatted = "Rating: $rating"
    text = formatted
}

@BindingAdapter("show")
fun View.setShow(show:Boolean){
    if(show)
        visibility = View.VISIBLE
    else
        visibility = View.GONE
}



