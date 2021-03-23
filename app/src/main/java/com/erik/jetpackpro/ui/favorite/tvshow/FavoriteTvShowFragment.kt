package com.erik.jetpackpro.ui.favorite.tvshow

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
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.databinding.FragmentFavoriteTvShowBinding
import com.erik.jetpackpro.ui.tvshows.detail.TvDetail
import com.example.awesomedialog.*
import kotlinx.android.synthetic.main.loading_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTvShowFragment : Fragment(), FavoriteTvInterface {
    private val favoriteTvViewModel: FavoriteTvViewModel by viewModel()

    private lateinit var fragmentFavoriteTvBinding: FragmentFavoriteTvShowBinding

    private val tvAdapter = FavoriteTvAdapter(this)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fragmentFavoriteTvBinding =
                FragmentFavoriteTvShowBinding.inflate(layoutInflater, container, false)
        return fragmentFavoriteTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            setupViewModel()
            itemTouchHelper.attachToRecyclerView(fragmentFavoriteTvBinding.rvTvFavorite)
        }
    }

    private fun setupViewModel() {
        loading_item.startShimmer()
        favoriteTvViewModel.getFavoriteTv().observe(viewLifecycleOwner) {
            loading_item.stopShimmer()
            loading_item.visibility = View.GONE

            tvAdapter.submitList(it)

            if (it.isEmpty()) {
                fragmentFavoriteTvBinding.noDataTv.visibility = View.VISIBLE
            } else {
                fragmentFavoriteTvBinding.rvTvFavorite.visibility = View.VISIBLE
            }
        }
        fragmentFavoriteTvBinding.rvTvFavorite.setHasFixedSize(true)
        fragmentFavoriteTvBinding.rvTvFavorite.adapter = tvAdapter

    }

    override fun onItemTvDetailClick(tv: TvDetailEntity) {
        val intent = Intent(activity, TvDetail::class.java)
        intent.putExtra(TvDetail.EXTRA_TV, tv.id)
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
                val tvDetailEntity = tvAdapter.getSwipedData(swipedPosition)

                activity?.let {
                    AwesomeDialog.build(it)
                            .title(
                                    resources.getString(com.erik.jetpackpro.R.string.delete_tv_favorite),
                                    titleColor = ContextCompat.getColor(it, android.R.color.black)
                            )
                            .icon(com.erik.jetpackpro.R.drawable.ask)
                            .background(com.erik.jetpackpro.R.drawable.layout_rounded_white)
                            .onPositive(
                                    resources.getString(com.erik.jetpackpro.R.string.OK),
                                    buttonBackgroundColor = R.drawable.layout_rounded_dark_black,
                                    textColor = ContextCompat.getColor(it, android.R.color.white),
                                    action = {
                                        tvDetailEntity?.let { favoriteTvViewModel.deleteFavorite(it) }
                                    }
                            )
                            .position(AwesomeDialog.POSITIONS.CENTER)
                }
            }
        }

    })
}