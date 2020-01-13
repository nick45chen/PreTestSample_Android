package com.example.fuglepretestsample.views.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fuglepretestsample.R
import com.example.fuglepretestsample.databinding.LayoutMainListItemBinding

class StocksViewAdapter : RecyclerView.Adapter<StocksViewAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_main_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.updateView()
    }

    inner class ItemViewHolder(private val itemViewBinding: LayoutMainListItemBinding) : RecyclerView.ViewHolder(itemViewBinding.root) {
        fun updateView() {
            itemViewBinding.apply {
                isUps = true
                txtName.text = "TestTest"
                txtSymbols.text = "Symbols"
                txtTime.text = "9999/99/99 99:99:99"
                txtPrice.text = "$9999.99"
            }
        }
    }
}