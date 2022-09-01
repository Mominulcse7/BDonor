package com.mominulcse7.bdonor.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.reea.base.EmptyHolder

abstract class BasePagingAdapter<T : Any>(
    @LayoutRes private val resource: Int,
    articleDiffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, EmptyHolder>(articleDiffCallback) {

    var clickListener: ClickListener = object : ClickListener {
        override fun onAdapterItemClick(itemView: View, any: Any, position: Int, button: String) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EmptyHolder(LayoutInflater.from(parent.context).inflate(resource, parent, false))

    override fun onBindViewHolder(holder: EmptyHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            bindView(holder.itemView, item, position)
        }
    }

    abstract fun bindView(view: View, any: T, position: Int)

    fun setItemClickListener(itemClickListener: ClickListener) {
        this.clickListener = itemClickListener
    }
}