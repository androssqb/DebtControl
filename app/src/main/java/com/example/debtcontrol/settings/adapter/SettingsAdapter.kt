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
//        holder.binding.icon.setImageResource(data[position].iconResourceId)
//        holder.binding.desc.text = context.resources.getString(data[position].descResourceId)
        holder.binding.settings = data[position]
        holder.binding.clickListener = clickListener
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ItemViewHolder(val binding: ItemSettingsBinding) : RecyclerView.ViewHolder(binding.root)
}

class SettingsListener(val clickListener: (descResourceId: Int) -> Unit) {
    fun onClick(settings: Settings) = clickListener(settings.descResourceId)
}