package com.tintash.themoviedb.model

data class Genre(
        val id : Int,
        val name : String
) {

    override fun toString(): String {
        return name
    }
}