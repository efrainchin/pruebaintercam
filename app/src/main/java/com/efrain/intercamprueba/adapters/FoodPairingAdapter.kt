package com.efrain.intercamprueba.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.efrain.intercamprueba.R
import com.efrain.intercamprueba.databinding.ItemFoodPairingBinding

class FoodPairingAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    private var items: List<String> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = FoodPairingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_food_pairing, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as FoodPairingViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int
        = items.size

    fun addItems(list: List<String>) {
        this.items = list
        notifyDataSetChanged()
    }

    private inner class FoodPairingViewHolder (private val binding: ItemFoodPairingBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) {
            binding.apply {
                tvDescription.text = item
            }
        }
    }

}
