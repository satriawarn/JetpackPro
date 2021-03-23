package com.erik.jetpackpro.ui.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.data.source.remote.response.tv.ResultsItemTv
import com.erik.jetpackpro.data.source.local.entity.TvShowEntity
import com.erik.jetpackpro.databinding.ListItemBinding
import com.erik.jetpackpro.utils.loadImage

class TvAdapter(private val tvInterface: TvInterface) :
PagedListAdapter<TvShowEntity, TvAdapter.ItemsHolder>(DIFF_CALLBACK) {
    companion object{
        private val DIFF_CALLBACK= object : DiffUtil.ItemCallback<TvShowEntity>(){
            override fun areItemsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShowEntity, newItem: TvShowEntity): Boolean {
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

    inner class ItemsHolder(private val binding: ListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(tv: TvShowEntity) {
            with(binding) {
                imgPoster.loadImage(BuildConfig.BASE_URL_POSTER_PATH_BIG + tv.posterPath)
                tvItemTitle.text = tv.name

                itemView.setOnClickListener {
                    tvInterface.onItemClick(tv)
                }
            }
        }
    }
}