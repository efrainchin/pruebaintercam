package com.efrain.intercamprueba.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.efrain.intercamprueba.R
import com.efrain.intercamprueba.databinding.ItemHopsBinding
import com.efrain.intercamprueba.entities.EntityMalt

class HoopsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> () {

    private var items: List<EntityMalt> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
            = HoopsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_hops, parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as HoopsViewHolder).bind(items[position])
    }

    override fun getItemCount(): Int
        = items.size

    fun addItems(list: List<EntityMalt>) {
        this.items = list
        notifyDataSetChanged()
    }

    private inner class HoopsViewHolder (private val binding: ItemHopsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: EntityMalt) {
            binding.apply {
                tvDescription.text = "${item.name} ${item.amount.value} ${item.amount.unit.first()}"
            }
        }
    }

}
