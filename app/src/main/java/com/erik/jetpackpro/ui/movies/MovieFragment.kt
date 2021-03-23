package com.erik.jetpackpro.ui.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.erik.jetpackpro.R
import com.erik.jetpackpro.data.source.local.entity.MovieEntity
import com.erik.jetpackpro.databinding.FragmentMovieBinding
import com.erik.jetpackpro.ui.movies.detail.MovieDetail
import com.erik.jetpackpro.ui.movies.detail.MovieDetail.Companion.EXTRA_MOVIE
import com.erik.jetpackpro.utils.SnackBar
import com.erik.jetpackpro.utils.Status
import kotlinx.android.synthetic.main.loading_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : Fragment(), MovieInterface {
    private val movieViewModel: MovieViewModel by viewModel()

    private lateinit var fragmentMovieBinding: FragmentMovieBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fragmentMovieBinding = FragmentMovieBinding.inflate(layoutInflater, container, false)
        return fragmentMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            setUpViewModel()
        }
    }

    private fun setUpViewModel() {
        val movieAdapter = MovieAdapter(this)

        movieViewModel.getDiscoverMovie().observe(viewLifecycleOwner) { movies ->
            when (movies.status) {
                Status.LOADING -> loading_item.startShimmer()
                Status.SUCCESS -> {
                    loading_item.stopShimmer()
                    loading_item.visibility = View.GONE
                    fragmentMovieBinding.rvMovie.visibility = View.VISIBLE

                    movieAdapter.submitList(movies.data)
                }
                Status.ERROR -> {
                    loading_item.stopShimmer()
                    loading_item.visibility = View.GONE
                    SnackBar.showSnackBar(
                            fragmentMovieBinding.root,
                            resources.getString(R.string.something_wrong)
                    )
                }
            }
        }

        with(fragmentMovieBinding.rvMovie) {
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    override fun onItemClick(movie: MovieEntity) {
        val intent = Intent(activity, MovieDetail::class.java)
        intent.putExtra(EXTRA_MOVIE, movie.id)
        startActivity(intent)
    }
}