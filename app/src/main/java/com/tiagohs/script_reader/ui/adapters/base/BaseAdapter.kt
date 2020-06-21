package com.tiagohs.script_reader.ui.adapters.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<I, V : BaseViewHolder<I>>(
        var list: List<I>
): RecyclerView.Adapter<V>() {

    abstract fun getLayoutResId(viewType: Int): Int
    abstract fun onCreateViewHolder(viewType: Int, view: View): V

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutResId(viewType), parent, false)

        return onCreateViewHolder(viewType, view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: V, position: Int) {
        if (list.isEmpty()) { return }

        holder.bind(list[position], position)
    }

}