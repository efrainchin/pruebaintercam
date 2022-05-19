package com.efrain.intercamprueba.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.efrain.intercamprueba.R
import com.efrain.intercamprueba.databinding.ItemMainBinding
import com.efrain.intercamprueba.entities.EntityBeers
import com.squareup.picasso.Picasso

class MainAdapter(private val listener: AddBeersDataBase)
    : ListAdapter<EntityBeers, RecyclerView.ViewHolder>(ListItemCallback()) {


    class ListItemCallback : DiffUtil.ItemCallback<EntityBeers>() {
        override fun areItemsTheSame(oldItem: EntityBeers, newItem: EntityBeers): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: EntityBeers, newItem: EntityBeers): Boolean {
            return oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as MainViewHolder).bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = MainViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_main, parent, false))


    private inner class MainViewHolder (private val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EntityBeers) {

            binding.apply {
                tvTitle.text = item.name
                tvDescription.text = item.contributedBy
                Picasso.get().load(item.imageUrl).into(ivLogo)
                ivFav.setOnClickListener {
                    listener.addBeer(item)
                }
            }

            binding.root.setOnClickListener {
                binding.root.findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundleOf("id" to item.id.toString()))
            }
        }
    }

    interface AddBeersDataBase {
        fun addBeer(beers: EntityBeers)
    }

}


