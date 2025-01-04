package com.landgorilla.movies.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.landgorilla.domain.model.Movie
import com.landgorilla.movies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var binding: ActivityMainBinding

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: MainViewModel by viewModels()

    private val adapter by lazy { MovieAdapter { movie -> onMovieClick(movie) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
            vm = viewModel
            this.adapter = this@MainActivity.adapter
        }
        setContentView(binding.root)

        observeViewModel()
        viewModel.fetchNextMovieList()
        setupSearchView()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.movieList.collectLatest { movieList ->
                adapter.submitList(movieList)
            }
        }

        lifecycleScope.launch {
            viewModel.toastMessage.collectLatest { event ->
                event?.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.searchMovies(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    viewModel.searchMovies(it)
                }
                return true
            }
        })
    }

    private fun onMovieClick(movie: Movie) {

    }

    private fun showToast(message: String) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}