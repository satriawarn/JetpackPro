package com.erik.jetpackpro.ui.favorite.tvshow

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.data.source.local.entity.TvDetailEntity
import com.erik.jetpackpro.databinding.ListItemBinding
import com.erik.jetpackpro.utils.loadImage

class FavoriteTvAdapter(private val favoriteTvInterface: FavoriteTvInterface) :
        PagedListAdapter<TvDetailEntity, FavoriteTvAdapter.ItemsHolder>(DIFF_CALLBACk) {

    companion object {
        private val DIFF_CALLBACk = object : DiffUtil.ItemCallback<TvDetailEntity>() {
            override fun areItemsTheSame(oldItem: TvDetailEntity, newItem: TvDetailEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvDetailEntity, newItem: TvDetailEntity): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemsHolder {
        val itemsTvBinding =
                ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemsHolder(itemsTvBinding)
    }

    override fun onBindViewHolder(holder: ItemsHolder, position: Int) {
        val tv = getItem(position)
        tv?.let { holder.bind(it) }
    }

    fun getSwipedData(swipedPosition: Int): TvDetailEntity? = getItem(swipedPosition)

    inner class ItemsHolder(private val binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvDetailEntity) {
            with(binding) {
                imgPoster.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + tv.posterPath)
                tvItemTitle.text = tv.name

                itemView.setOnClickListener {
                    favoriteTvInterface.onItemTvDetailClick(tv)
                }
            }
        }
    }
}