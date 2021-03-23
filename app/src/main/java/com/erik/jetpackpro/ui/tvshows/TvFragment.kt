package com.erik.jetpackpro.ui.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import com.erik.jetpackpro.R
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity
import com.erik.jetpackpro.databinding.FragmentTvBinding
import com.erik.jetpackpro.ui.tvshows.detail.TvDetail
import com.erik.jetpackpro.ui.tvshows.detail.TvDetail.Companion.EXTRA_TV
import com.erik.jetpackpro.utils.SnackBar
import com.erik.jetpackpro.utils.Status
import kotlinx.android.synthetic.main.loading_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class TvFragment : Fragment(), TvInterface {
    private val tvViewModel: TvViewModel by viewModel()

    private lateinit var fragmentTvBinding: FragmentTvBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fragmentTvBinding = FragmentTvBinding.inflate(layoutInflater, container, false)
        return fragmentTvBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            setUpViewModel()
        }
    }

    private fun setUpViewModel() {
        val tvAdapter = TvAdapter(this)

        tvViewModel.getDiscoverTv().observe(viewLifecycleOwner) { tvShow ->
            when(tvShow.status){
                Status.LOADING -> loading_item.startShimmer()
                Status.SUCCESS -> {
                    loading_item.stopShimmer()
                    loading_item.visibility = View.GONE
                    fragmentTvBinding.rvTv.visibility = View.VISIBLE

                    tvAdapter.submitList(tvShow.data)
                }
                Status.ERROR -> {
                    loading_item.stopShimmer()
                    loading_item.visibility = View.GONE
                    SnackBar.showSnackBar(
                        fragmentTvBinding.root,
                        resources.getString(R.string.something_wrong)
                    )
                }
            }
        }

        with(fragmentTvBinding.rvTv) {
            setHasFixedSize(true)
            adapter = tvAdapter
        }
    }

    override fun onItemClick(tv: TvShowEntity) {
        val intent = Intent(activity, TvDetail::class.java)
        intent.putExtra(EXTRA_TV, tv.id)
        startActivity(intent)
    }
}