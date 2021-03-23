package com.erik.jetpackpro.ui.movies.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.erik.jetpackpro.BuildConfig
import com.erik.jetpackpro.data.source.remote.response.moviedetail.ProductionCompany
import com.erik.jetpackpro.databinding.ItemProductionsBinding
import com.erik.jetpackpro.utils.loadImage

class AdapterProduction(val data: MutableList<ProductionCompany>) :
        RecyclerView.Adapter<AdapterProduction.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemProductionsBinding =
                ItemProductionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemProductionsBinding)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val production = data[position]
        holder.bind(production)
    }

    class ViewHolder(private val binding: ItemProductionsBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(production: ProductionCompany) {
            with(binding) {
                name.text = production.name
                image.loadImage(BuildConfig.BASE_URL_POSTER_PATH_SMALL + production.logoPath)
            }
        }
    }
}