package com.example.debtcontrol.settings.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.debtcontrol.Constants
import com.example.debtcontrol.databinding.ItemSettingsBinding
import com.example.debtcontrol.settings.model.Settings


class SettingsAdapter(
        private val context: Context,
        private val dataset: List<Settings>
) : RecyclerView.Adapter<SettingsAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ItemSettingsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.icon.setImageResource(dataset[position].iconResourceId)
        holder.binding.desc.text = context.resources.getString(dataset[position].descResourceId)
        holder.binding.layout.setOnClickListener {
            Constants.showToast(context, dataset[position].descResourceId)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    class ItemViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)
}