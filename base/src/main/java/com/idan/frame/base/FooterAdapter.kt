package com.idan.frame.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.idan.frame.databinding.FooterLayoutBinding

class FooterAdapter(private val adapter: PagingDataAdapter<*, *>) :
    LoadStateAdapter<FooterAdapter.FooterHolder>() {


    class FooterHolder(private val binding: FooterLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindState(loadState: LoadState, adapter: PagingDataAdapter<*, *>) {
            when (loadState) {
                is LoadState.Error -> {
                    binding.tvFooter.visibility = View.VISIBLE
                    binding.tvFooter.text = "加载失败，点击重试"
                    binding.tvFooter.setOnClickListener {
                        adapter.retry()
                    }
                }
                is LoadState.Loading -> {
                    binding.tvFooter.visibility = View.VISIBLE
                    binding.tvFooter.text = "加载中.."
                }
                is LoadState.NotLoading -> {
                    binding.tvFooter.visibility = View.GONE
                }
            }
        }
    }

    override fun onBindViewHolder(holder: FooterHolder, loadState: LoadState) {
        holder.bindState(loadState, adapter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): FooterHolder {
        return FooterHolder(
            FooterLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}