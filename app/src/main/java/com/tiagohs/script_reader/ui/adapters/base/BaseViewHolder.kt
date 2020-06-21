package com.tiagohs.script_reader.ui.adapters.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T>(override val containerView: View): RecyclerView.ViewHolder(containerView), LayoutContainer {

    var item: T? = null

    open fun bind(item: T, position: Int) {
        this.item = item
    }
}