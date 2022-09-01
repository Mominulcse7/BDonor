package com.mominulcse7.bdonor.views.adapters

import android.view.View

interface ClickListener {
    fun onAdapterItemClick(itemView: View, any: Any, position: Int, button: String)
}
