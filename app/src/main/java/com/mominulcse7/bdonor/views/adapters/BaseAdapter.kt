package com.mominulcse7.bdonor.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.reea.base.EmptyHolder

abstract class BaseAdapter(@LayoutRes private val resource: Int) :
    RecyclerView.Adapter<EmptyHolder>() {

    private var items: MutableList<Any> = ArrayList()

    var clickListener: ClickListener = object : ClickListener {
        override fun onAdapterItemClick(itemView: View, any: Any, position: Int, button: String) {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EmptyHolder(LayoutInflater.from(parent.context).inflate(resource, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: EmptyHolder, position: Int) {
        bindView(holder.itemView, items[position], position)
    }

    abstract fun bindView(view: View, any: Any, position: Int = 0)

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

    fun setItemClickListener(itemClickListener: ClickListener) {
        this.clickListener = itemClickListener
    }
}