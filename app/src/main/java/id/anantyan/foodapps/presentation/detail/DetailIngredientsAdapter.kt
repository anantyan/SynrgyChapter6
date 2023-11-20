package id.anantyan.foodapps.presentation.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.foodapps.databinding.ListItemDetailIngredientsBinding
import id.anantyan.foodapps.domain.model.ExtendedIngredientsItem

class DetailIngredientsAdapter : ListAdapter<ExtendedIngredientsItem, DetailIngredientsAdapter.ExtendedIngredientsItemViewHolder>(
    ExtendedIngredientsItemComparator
) {

    private object ExtendedIngredientsItemComparator :
        DiffUtil.ItemCallback<ExtendedIngredientsItem>() {
        override fun areItemsTheSame(
            oldItem: ExtendedIngredientsItem,
            newItem: ExtendedIngredientsItem
        ): Boolean {
            return oldItem.original == newItem.original
        }

        override fun areContentsTheSame(
            oldItem: ExtendedIngredientsItem,
            newItem: ExtendedIngredientsItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExtendedIngredientsItemViewHolder {
        return ExtendedIngredientsItemViewHolder(
            ListItemDetailIngredientsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ExtendedIngredientsItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class ExtendedIngredientsItemViewHolder(private val binding: ListItemDetailIngredientsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindItem(item: ExtendedIngredientsItem) {
            binding.txtValue.text = "${bindingAdapterPosition+1}. ${item.original}"
        }
    }
}