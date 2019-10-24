package com.tintash.themoviedb

import com.tintash.themoviedb.model.Movie
import com.tintash.themoviedb.viewmodel.DetailViewModel
import junit.framework.Assert
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DetailVeiwModelTest : KoinTest {

    lateinit var viewModel : DetailViewModel

    @Mock
    lateinit var movie:Movie

    @Before
    fun setup(){
        viewModel = DetailViewModel()
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun testSetMovieItem(){

        viewModel.setMovieItem(movie)
        Assert.assertEquals(movie, viewModel.movie)
    }

}