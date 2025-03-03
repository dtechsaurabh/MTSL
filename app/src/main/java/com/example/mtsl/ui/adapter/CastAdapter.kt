//package com.example.mtsl.ui.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import com.bumptech.glide.Glide
//import com.example.mtsl.R
//import com.example.mtsl.databinding.ItemCastBinding
//import com.example.mtsl.models.CastMember
//
//class CastAdapter : ListAdapter<CastMember, CastAdapter.CastViewHolder>(DiffCallback()) {
//
//    class CastViewHolder(private val binding: ItemCastBinding) : RecyclerView.ViewHolder(binding.root) {
//        fun bind(castMember: CastMember) {
//            binding.castName.text = castMember.name
//            Glide.with(binding.root)
//                .load("https://image.tmdb.org/t/p/w200${castMember.profilePath}")
//                .placeholder(R.drawable.sample_poster)
//                .into(binding.castImage)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
//        val binding = ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return CastViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
//        holder.bind(getItem(position))
//    }
//
//    class DiffCallback : DiffUtil.ItemCallback<CastMember>() {
//        override fun areItemsTheSame(oldItem: CastMember, newItem: CastMember) = oldItem.name == newItem.name
//        override fun areContentsTheSame(oldItem: CastMember, newItem: CastMember) = oldItem == newItem
//    }
//}
