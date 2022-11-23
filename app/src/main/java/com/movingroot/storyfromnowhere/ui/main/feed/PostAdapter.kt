package com.movingroot.storyfromnowhere.ui.main.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.movingroot.storyfromnowhere.data.model.Post
import com.movingroot.storyfromnowhere.databinding.AdapterPostBinding

class PostAdapter : ListAdapter<Post, PostAdapter.ViewHolder>(PostDiffUtilItemCallback) {
    inner class ViewHolder(val binding: AdapterPostBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.post = getItem(position)
            binding.post?.let {
                binding.labels.text = makeLabelsText()
            }
        }

        private fun makeLabelsText(): String {
            val builder = StringBuilder()
            binding.post!!.labels.forEach { label ->
                builder.append(label.label).append(", ")
            }
            return builder.trim().dropLast(1).toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterPostBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }
}

object PostDiffUtilItemCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.postId == newItem.postId
}
