package com.mominulcse7.bdonor.views.adapters.base

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter() : RecyclerView.Adapter<EmptyHolder>() {

    private var items: MutableList<Any> = ArrayList()

    var clickListener: ClickListener = object : ClickListener {
        override fun onAdapterItemClick(itemView: View, any: Any, position: Int, button: String) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = inflater(parent, viewType)

    override fun onBindViewHolder(holder: EmptyHolder, position: Int) {
        bindView(holder.viewBinding, items[position], position)
    }

    abstract fun bindView(view: ViewBinding, any: Any, position: Int = 0)

    abstract fun inflater(parent: ViewGroup, viewType: Int): EmptyHolder

    override fun getItemCount() = items.size

    fun setItemList(itemList: MutableList<Any>) {
        items = itemList
        notifyDataSetChanged()
    }

    fun clearItem() {
        items = ArrayList()
        notifyDataSetChanged()
    }

    fun addItem(item: Any) {
        items.add(item)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyDataSetChanged()
    }

    fun setAdapterClickListener(adapterClickListener: ClickListener) {
        this.clickListener = adapterClickListener
    }
}