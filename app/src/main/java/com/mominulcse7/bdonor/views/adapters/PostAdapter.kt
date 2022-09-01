package com.mominulcse7.bdonor.views.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.mominulcse7.bdonor.databinding.ItemPostBinding
import com.mominulcse7.bdonor.model.PostModel
import com.mominulcse7.bdonor.views.adapters.base.BasePagingAdapter
import com.mominulcse7.bdonor.views.adapters.base.EmptyHolder
import javax.inject.Inject

class PostA @Inject constructor() :
    BasePagingAdapter<PostModel>(ARTICLE_DIFF_CALLBACK) {

    override fun inflater(parent: ViewGroup, viewType: Int): EmptyHolder {
        return EmptyHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun bindView(viewBinding: ViewBinding, any: PostModel, position: Int) {
        with(viewBinding as ItemPostBinding) {
//            tvName.text = "Title: "

            tvName.setOnClickListener {
                clickListener.onAdapterItemClick(
                    tvName,
                    any,
                    position,
                    ""
                )
            }
        }
    }
}

val ARTICLE_DIFF_CALLBACK = object : DiffUtil.ItemCallback<PostModel>() {
    override fun areItemsTheSame(oldItem: PostModel, newItem: PostModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: PostModel, newItem: PostModel) =
        oldItem.equals(newItem)
}
