package com.tintash.themoviedb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.tintash.themoviedb.model.*
import com.tintash.themoviedb.network.imageLoader
import com.tintash.themoviedb.network.networkModule
import com.tintash.themoviedb.viewmodel.MainViewModel
import com.tintash.themoviedb.viewmodel.viewModelModule
import junit.framework.Assert.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.stubbing.Answer


class MainViewModelTest : KoinTest {


    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    val dispatcher = TestCoroutineDispatcher()
    val scope = TestCoroutineScope(dispatcher)

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = CoroutineTestRule()

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @Mock
    lateinit var moviesRepo: MoviesRepo
    @Mock
    lateinit var genreRepo: GenreRepo

    @Mock
    lateinit var movieObject:Movie

    lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        startKoin {
            modules(listOf(networkModule, viewModelModule, trendingMoviesModule, genresModule, imageLoader))
        }

        MockitoAnnotations.initMocks(this)
        viewModel = MainViewModel(moviesRepo, genreRepo)
    }


    @After
    fun after() {
        stopKoin()
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testNull() {

        scope.runBlockingTest {

            Mockito.`when`(moviesRepo.getTrendingMovies(1)).thenAnswer(Answer { null })

            viewModel.fetchTrendingMovies()

            assertEquals(0,viewModel.moviesList.size)
            assertEquals(1, viewModel.pageNumber)


        }

    }

    @ExperimentalCoroutinesApi
    @Test
    fun testNotNull() {

        val result = ArrayList<Movie>()
        result.add(movieObject)
        scope.runBlockingTest {

            Mockito.`when`(moviesRepo.getTrendingMovies(1)).thenAnswer(Answer { result })


            viewModel.fetchTrendingMovies()

            assertEquals(1,viewModel.moviesList.size)
            assertEquals(2, viewModel.pageNumber)


        }

    }
    @ExperimentalCoroutinesApi
    @Test
    fun testGenreNull(){
        scope.runBlockingTest {

            Mockito.`when`(genreRepo.getGenres()).thenAnswer(Answer { null })

            viewModel.fetchGenres()

            viewModel.genresFetched.observeForTesting {

                assertNull(viewModel.genresList)
                assertFalse(viewModel.genresFetched.value!!)
            }


        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGenreNotNull(){
        scope.runBlockingTest {

            Mockito.`when`(genreRepo.getGenres()).thenAnswer(Answer { ArrayList<Genre>() })

            viewModel.fetchGenres()

            viewModel.genresFetched.observeForTesting {

                assertNotNull(viewModel.genresList)
                assertTrue(viewModel.genresFetched.value!!)
            }


        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testGenreSelected(){

        val genreId = 5

        val result =  ArrayList<Movie>()
        result.add(movieObject)
        result.add(movieObject)
        result.add(movieObject)
        scope.runBlockingTest {

            Mockito.`when`(moviesRepo.getTrendingMoviesWithGenre(1, genreId )).thenAnswer(Answer {result })

            viewModel.genreSelected(Genre(genreId,"Testing"))

            assertEquals(genreId,viewModel.selectedGenreId)

            assertEquals(2,viewModel.pageNumber)

            assertEquals(3,viewModel.moviesList.size)



        }
    }


}


/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<T> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}