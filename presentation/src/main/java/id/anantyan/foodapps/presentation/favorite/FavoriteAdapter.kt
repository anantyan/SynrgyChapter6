package id.anantyan.foodapps.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.ViewSizeResolver
import id.anantyan.foodapps.common.R
import id.anantyan.foodapps.presentation.databinding.ListItemHomeBinding
import id.anantyan.foodapps.domain.model.FoodModel

class FavoriteAdapter : ListAdapter<FoodModel, FavoriteAdapter.FoodModelViewHolder>(FoodModelComparator) {

    private var _onClick: ((position: Int, item: FoodModel) -> Unit)? = null

    private object FoodModelComparator : DiffUtil.ItemCallback<FoodModel>() {
        override fun areItemsTheSame(oldItem: FoodModel, newItem: FoodModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FoodModel, newItem: FoodModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodModelViewHolder {
        return FoodModelViewHolder(
            ListItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class FoodModelViewHolder(private val binding: ListItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: FoodModel) {
            binding.imgHeadline.load(item.image) {
                crossfade(true)
                placeholder(R.drawable.img_loading)
                error(R.drawable.img_not_found)
                size(ViewSizeResolver(binding.imgHeadline))
            }
            binding.txtServings.text = item.servings.toString()
            binding.txtTitle.text = item.title.toString()
            binding.txtReadyInMinutes.text = item.readyInMinutes.toString()
        }
    }

    fun onClick(listener: (position: Int, item: FoodModel) -> Unit) {
        _onClick = listener
    }
}