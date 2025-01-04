package com.landgorilla.movies.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.landgorilla.movies.ui.main.MainViewModel
import com.landgorilla.movies.utils.RecyclerViewPagination

object RecyclerViewBinding {

    @JvmStatic
    @BindingAdapter("adapter")
    fun bindRecyclerViewAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
        view.adapter = adapter.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    @JvmStatic
    @BindingAdapter("paginationMovieList")
    fun bindPagination(view: RecyclerView, viewModel: MainViewModel) {
        RecyclerViewPagination(
            recyclerView = view,
            isLoading = { viewModel.isLoading.value },
            loadMore = { viewModel.fetchNextMovieList() },
            onLast = { false }
        ).run {
            threshold = 8
        }
    }
}