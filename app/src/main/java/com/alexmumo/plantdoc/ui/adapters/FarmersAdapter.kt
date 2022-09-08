package com.alexmumo.plantdoc.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.alexmumo.plantdoc.data.entity.User
import com.alexmumo.plantdoc.databinding.FarmerItemBinding

class FarmersAdapter : ListAdapter<User, FarmersAdapter.CustomViewHolder>(FARMERCOMPARATOR) {
    inner class CustomViewHolder(private val binding: FarmerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User?) {
            binding.farmersName.text = user?.name
            binding.farmersAddress.text = user?.email
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): FarmersAdapter.CustomViewHolder {
        return CustomViewHolder(
            FarmerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
    override fun onBindViewHolder(holder: FarmersAdapter.CustomViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}

object FARMERCOMPARATOR : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}
