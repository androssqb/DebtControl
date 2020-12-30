package com.example.debtcontrol.mydebtors.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.debtcontrol.Constants
import com.example.debtcontrol.database.Debt
import com.example.debtcontrol.databinding.ItemDebtListBinding


class DebtAdapter(private val clickListener: DebtListener) : ListAdapter<Debt, DebtAdapter.ViewHolder>(DebtDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    class ViewHolder private constructor(val binding: ItemDebtListBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Debt, clickListener: DebtListener) {
            binding.debt = item
            binding.clickListener = clickListener
            binding.sum.paint.shader = Constants.setGradient(binding.sum.textSize)
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val binding = ItemDebtListBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class DebtDiffCallback : DiffUtil.ItemCallback<Debt>() {
    override fun areItemsTheSame(oldItem: Debt, newItem: Debt): Boolean {
        return oldItem.debtId == newItem.debtId
    }

    override fun areContentsTheSame(oldItem: Debt, newItem: Debt): Boolean {
        return oldItem == newItem
    }
}

class DebtListener(val clickListener: (debtId: Long) -> Unit) {
    fun onClick(debt: Debt) = clickListener(debt.debtId)
}