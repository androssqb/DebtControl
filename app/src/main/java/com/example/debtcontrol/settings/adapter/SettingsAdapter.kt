package com.example.debtcontrol.settings.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.debtcontrol.databinding.ItemSettingsBinding
import com.example.debtcontrol.settings.model.Settings


class SettingsAdapter(private val clickListener: SettingsListener)
    : RecyclerView.Adapter<SettingsAdapter.ItemViewHolder>() {

    var data = listOf<Settings>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemSettingsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(data[position], clickListener)
    }

    override fun getItemCount(): Int = data.size

    class ItemViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Settings, clickListener: SettingsListener) {
            binding.settings = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }
}

class SettingsListener(val clickListener: (descResourceId: Int) -> Unit) {
    fun onClick(settings: Settings) = clickListener(settings.descResourceId)
}