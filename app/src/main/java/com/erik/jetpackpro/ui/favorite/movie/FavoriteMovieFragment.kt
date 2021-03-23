package com.erik.jetpackpro.ui.favorite.movie

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.erik.jetpackpro.R
import com.erik.jetpackpro.data.source.local.entity.MovieDetailEntity
import com.erik.jetpackpro.databinding.FragmentFavoriteMovieBinding
import com.erik.jetpackpro.ui.movies.detail.MovieDetail
import com.erik.jetpackpro.utils.SnackBar
import com.example.awesomedialog.*
import kotlinx.android.synthetic.main.loading_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteMovieFragment : Fragment(), FavoriteInterface {
    private val favoriteMovieViewModel: FavoriteMovieViewModel by viewModel()

    private lateinit var fragmentFavoriteMovieBinding: FragmentFavoriteMovieBinding

    private val movieAdapter = FavoriteMovieAdapter(this)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fragmentFavoriteMovieBinding =
                FragmentFavoriteMovieBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteMovieBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            setupViewModel()
            itemTouchHelper.attachToRecyclerView(fragmentFavoriteMovieBinding.rvMovieFavorite)
        }
    }

    private fun setupViewModel() {
        loading_item.startShimmer()
        favoriteMovieViewModel.getFavoriteMovie().observe(viewLifecycleOwner) {
            loading_item.stopShimmer()
            loading_item.visibility = View.GONE

            movieAdapter.submitList(it)

            if (it.isEmpty()) {
                fragmentFavoriteMovieBinding.noData.visibility = View.VISIBLE
            } else {
                fragmentFavoriteMovieBinding.rvMovieFavorite.visibility = View.VISIBLE
            }
        }
        fragmentFavoriteMovieBinding.rvMovieFavorite.setHasFixedSize(true)
        fragmentFavoriteMovieBinding.rvMovieFavorite.adapter = movieAdapter
    }

    override fun onItemDetailClick(movie: MovieDetailEntity) {
        val intent = Intent(activity, MovieDetail::class.java)
        intent.putExtra(MovieDetail.EXTRA_MOVIE, movie.id)
        startActivity(intent)
    }

    private val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
        override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
        ): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

        override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            if (view != null) {
                val swipedPosition = viewHolder.adapterPosition
                val movieDetailEntity = movieAdapter.getSwipedData(swipedPosition)

                activity?.let {
                    AwesomeDialog.build(it)
                            .title(
                                    resources.getString(com.erik.jetpackpro.R.string.delete_movie_favorite),
                                    titleColor = ContextCompat.getColor(it, android.R.color.black)
                            )
                            .icon(com.erik.jetpackpro.R.drawable.ask)
                            .background(com.erik.jetpackpro.R.drawable.layout_rounded_white)
                            .onPositive(
                                    resources.getString(com.erik.jetpackpro.R.string.OK),
                                    buttonBackgroundColor = R.drawable.layout_rounded_dark_black,
                                    textColor = ContextCompat.getColor(it, android.R.color.white),
                                    action = {
                                        movieDetailEntity?.let { favoriteMovieViewModel.deleteFavorite(it) }
                                    }
                            )
                            .position(AwesomeDialog.POSITIONS.CENTER)
                }
            }
        }
    })
}