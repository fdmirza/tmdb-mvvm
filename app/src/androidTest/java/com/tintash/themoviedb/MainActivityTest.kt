package com.tintash.themoviedb

import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.test.rule.ActivityTestRule
import com.tintash.themoviedb.model.Movie
import com.tintash.themoviedb.view.MainActivity
import com.tintash.themoviedb.view.MovieListAdapter
import com.tintash.themoviedb.viewmodel.MainViewModel
import junit.framework.Assert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.stubbing.Answer

class MainActivityTest : KoinTest{

    @get:Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, true, false)
     lateinit var vm:MainViewModel

    @Before
    fun setup(){
        vm = mock(MainViewModel::class.java)

        loadKoinModules(module {
            viewModel {
                vm
            }
        })
    }

    @After
    fun cleanUp() {
        stopKoin()
    }

    @Test
    fun test(){
val ld = MutableLiveData<List<Movie>>()
        Mockito.`when`(vm.moviesList).thenAnswer(Answer { ld })
        rule.launchActivity(null)

        ld.value = ArrayList<Movie>()

        val adapter = (rule.activity as MainActivity).findViewById<RecyclerView>(R.id.rvMovies).adapter as MovieListAdapter
        Assert.assertEquals(0, adapter.movies.size)

    }
}