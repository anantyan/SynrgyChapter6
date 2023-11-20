package id.anantyan.foodapps.presentation.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.foodapps.databinding.ListItemDetailInstructionsBinding
import id.anantyan.foodapps.domain.model.StepsItem

class DetailInstructionsAdapter : ListAdapter<StepsItem, DetailInstructionsAdapter.StepsItemViewHolder>(StepsItemComparator) {

    private object StepsItemComparator : DiffUtil.ItemCallback<StepsItem>() {
        override fun areItemsTheSame(oldItem: StepsItem, newItem: StepsItem): Boolean {
            return oldItem.step == newItem.step
        }

        override fun areContentsTheSame(oldItem: StepsItem, newItem: StepsItem): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepsItemViewHolder {
        return StepsItemViewHolder(
            ListItemDetailInstructionsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StepsItemViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class StepsItemViewHolder(private val binding: ListItemDetailInstructionsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindItem(item: StepsItem) {
            binding.txtValue.text = "${bindingAdapterPosition+1}. ${item.step}"
        }
    }
}