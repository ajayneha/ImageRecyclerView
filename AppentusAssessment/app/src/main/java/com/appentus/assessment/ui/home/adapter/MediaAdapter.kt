package com.appentus.assessment.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.appentus.assessment.databinding.MediaListItemBinding
import com.appentus.assessment.model.MediaResponse

class MediaAdapter : ListAdapter<MediaResponse, MediaAdapter.MediaViewHolder>(
    OffersListDiffCallback()
) {

    class OffersListDiffCallback : DiffUtil.ItemCallback<MediaResponse>() {

        override fun areItemsTheSame(oldItem: MediaResponse, newItem: MediaResponse): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: MediaResponse, newItem: MediaResponse): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.apply {
            onBind(getItem(position), position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(MediaListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    inner class MediaViewHolder(private val mBinding: MediaListItemBinding) : RecyclerView.ViewHolder(mBinding.root) {

        fun onBind(item: MediaResponse, position: Int) {
            mBinding.apply {
                data = item
                itemView.setOnClickListener {

                }
                executePendingBindings()
            }
        }
    }
}