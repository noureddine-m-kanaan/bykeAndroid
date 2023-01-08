package com.example.afya.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.afya.databinding.ItemExerciceBinding
import com.example.afya.view.placeholder.PlaceholderContent.PlaceholderItem

class TripsAdapter(
    private val clickOnItem: (String) -> Unit
) : ListAdapter<PlaceholderItem, TripsAdapter.ViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemExerciceBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<PlaceholderItem>() {
        override fun areItemsTheSame(oldItem: PlaceholderItem, newItem: PlaceholderItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PlaceholderItem,
            newItem: PlaceholderItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: ItemExerciceBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlaceholderItem) {
            binding.content.text = item.content
            binding.buttonDetails.setOnClickListener {
                clickOnItem(item.id)
            }
        }
    }
}