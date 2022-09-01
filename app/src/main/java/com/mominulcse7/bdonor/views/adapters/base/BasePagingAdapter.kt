package com.mominulcse7.bdonor.views.adapters.base

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding

abstract class BasePagingAdapter<T : Any>(
    articleDiffCallback: DiffUtil.ItemCallback<T>
) : PagingDataAdapter<T, EmptyHolder>(articleDiffCallback) {

    var clickListener: ClickListener = object : ClickListener {
        override fun onAdapterItemClick(itemView: View, any: Any, position: Int, button: String) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = inflater(parent, viewType)

    override fun onBindViewHolder(holder: EmptyHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            bindView(holder.viewBinding, item, position)
        }
    }

    abstract fun bindView(view: ViewBinding, any: T, position: Int)

    abstract fun inflater(parent: ViewGroup, viewType: Int): EmptyHolder

    fun setAdapterClickListener(adapterClickListener: ClickListener) {
        this.clickListener = adapterClickListener
    }
}