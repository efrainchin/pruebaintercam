package com.efrain.intercamprueba.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.efrain.intercamprueba.R
import com.efrain.intercamprueba.databinding.ItemFavBinding
import com.efrain.intercamprueba.entities.EntityBeers
import com.squareup.picasso.Picasso
import kotlin.random.Random

class FavoritesAdapter(private val listener: DeleteFavDataBase) : RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    private var items: List<EntityBeers> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = FavoritesViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_fav, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FavoritesViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int
            = items.size

    fun addItems(list: List<EntityBeers>) {
        this.items = list
        notifyDataSetChanged()
    }

    private inner class FavoritesViewHolder (private val binding: ItemFavBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EntityBeers) {
            binding.apply {
                tvTitle.text = item.name
                tvDescription.text = item.contributedBy
                Picasso.get().load(item.imageUrl).into(ivLogo)
                ratingBar.rating = Random.nextInt(1, 5).toFloat()

                imageButton.setOnClickListener {
                    listener.deleteBeer(item)
                }
            }

            binding.root.setOnClickListener {
                binding.root.findNavController().navigate(R.id.action_favoritesFragment_to_detailFragment, bundleOf("id" to item.id.toString()))
            }
        }
    }

    interface DeleteFavDataBase {
        fun deleteBeer(beers: EntityBeers)
    }
}

