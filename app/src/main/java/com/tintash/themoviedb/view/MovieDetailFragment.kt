package com.tintash.themoviedb.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tintash.themoviedb.R
import com.tintash.themoviedb.databinding.DetailBottomSheetBinding
import com.tintash.themoviedb.network.ImageLoader
import com.tintash.themoviedb.viewmodel.DetailViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


const val KEY_MOVIE_ITEM = "movie_item"

class MovieDetailFragment : BottomSheetDialogFragment() {

    val viewModel: DetailViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        viewModel.setMovieItem(arguments?.getParcelable(KEY_MOVIE_ITEM))
        val binding = DataBindingUtil.inflate<DetailBottomSheetBinding>(inflater, R.layout.detail_bottom_sheet, container, false)

        binding.viewModel = viewModel
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}
