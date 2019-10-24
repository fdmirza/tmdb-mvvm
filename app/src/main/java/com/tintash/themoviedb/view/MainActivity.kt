package com.tintash.themoviedb.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener
import com.tintash.themoviedb.base.BaseActivity
import com.tintash.themoviedb.databinding.ActivityMainBinding
import com.tintash.themoviedb.model.Genre
import com.tintash.themoviedb.model.Movie
import com.tintash.themoviedb.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.app.DatePickerDialog
import android.widget.DatePicker
import com.tintash.themoviedb.R
import java.util.*


class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), MovieListAdapter.Interaction {


    override fun movieItemClicked(model: Movie) {

        val movieDetailFragment = MovieDetailFragment()
        movieDetailFragment.arguments = Bundle().also {
            it.putParcelable(KEY_MOVIE_ITEM, model)
        }
        movieDetailFragment.show(supportFragmentManager, movieDetailFragment.tag)
    }

    private val viewModel: MainViewModel by viewModel()
    private var adapter: MovieListAdapter? = null

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    fun setObservers() {
        viewModel.moviesFetched.observe(this, Observer { _ ->
            adapter?.notifyDataSetChanged()

        })

        viewModel.error.observe(this, Observer { error ->

            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })

        viewModel.genresFetched.observe(this, Observer { fetched ->

            if (fetched == true)
                dataBinding.btnType.isEnabled = true
        })

        viewModel.typeSelection.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                showGenreSelector()
            }
        })


        viewModel.dateSelection.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                showDateSeletor()
            }
        })
    }

    fun showGenreSelector() {
        val builderSingle = AlertDialog.Builder(this)
        builderSingle.setTitle("Select Movie Type")
        val adapter = ArrayAdapter<Genre>(this, android.R.layout.simple_dropdown_item_1line, viewModel.genresList)
        builderSingle.setAdapter(adapter) { dialog, which ->
            viewModel.genreSelected(viewModel.genresList?.get(which))
            dialog.dismiss()
        }

        builderSingle.show()

    }

    fun showDateSeletor(){
        DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener{
            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

                viewModel.filterByDate(year,month,dayOfMonth)
            }

        }, viewModel.calendar
                .get(Calendar.YEAR), viewModel.calendar.get(Calendar.MONTH),
                viewModel.calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = MovieListAdapter(this, viewModel.displayList,get())
        dataBinding.viewModel = viewModel
        if (savedInstanceState == null) {
            viewModel.fetchTrendingMovies()
            viewModel.fetchGenres()
        }
        dataBinding.rvMovies.let {
            it.layoutManager = GridLayoutManager(this, resources.getInteger(R.integer.column_count))
            it.adapter = adapter
        }

        setObservers()
        setPagination()
    }




    private fun setPagination() {
        rvMovies.addOnScrollListener(object : InfiniteScrollListener(20, rvMovies.layoutManager as GridLayoutManager) {
            override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
                viewModel.fetchTrendingMovies()
            }

        })

    }

}
