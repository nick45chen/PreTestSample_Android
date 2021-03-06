package com.example.fuglepretestsample.views.main

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.fuglepretestsample.databinding.LayoutMainListItemBinding
import com.example.fuglepretestsample.models.Stock
import com.example.fuglepretestsample.views.web.WebViewActivity
import java.util.*


class StocksViewAdapter : RecyclerView.Adapter<StocksViewAdapter.ItemViewHolder>() {

    private val list: MutableList<Stock> by lazy { mutableListOf<Stock>() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                com.example.fuglepretestsample.R.layout.layout_main_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.updateView(list[position])
    }

    fun updateData(data: List<Stock>?) {
        list.clear()

        data?.apply {
            list.addAll(this)
        }

        notifyDataSetChanged()
    }

    private fun convertTimestampToDate(timestamp: Long): String {
        return try {
            val cal = Calendar.getInstance(Locale.getDefault())
            cal.timeInMillis = timestamp
            DateFormat.format("yyyy-MM-dd hh:mm:ss", cal).toString()
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    inner class ItemViewHolder(private val itemViewBinding: LayoutMainListItemBinding) :
        RecyclerView.ViewHolder(itemViewBinding.root) {
        fun updateView(data: Stock) {
            itemViewBinding.apply {
                isUps = true
                txtName.text = data.name
                txtSymbols.text = data.symbols
                txtTime.text = convertTimestampToDate(data.time)
                txtPrice.text = String.format("$%s", data.price)
                //
                root.setOnClickListener {
                    it.context.startActivity(
                        WebViewActivity.intentToSymbolsDetailView(
                            it.context,
                            data.symbols
                        )
                    )
                }
            }
        }
    }
}