package com.tiagohs.script_reader.ui.adapters

import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.Constraints
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import com.tiagohs.helpers.extensions.*
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.adapters.base.BaseAdapter
import com.tiagohs.script_reader.ui.adapters.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_script_simple.view.*

class ScriptSimpleAdapter(
    scriptList: List<Script>
): BaseAdapter<Script, ScriptSimpleAdapter.ScriptViewHolder>(scriptList) {

    var onScriptClicked: ((script: Script) -> Unit)? = null
    var onCategoryClicked: ((category: Category) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = R.layout.adapter_script_simple

    override fun onCreateViewHolder(viewType: Int, view: View): ScriptViewHolder = ScriptViewHolder(view)

    inner class ScriptViewHolder(itemView: View): BaseViewHolder<Script>(itemView), LayoutContainer {

        override fun bind(item: Script, position: Int) {
            super.bind(item, position)

            itemView.scriptTitle.setResourceText(item.title)

            setupPoster(item)
            setupIconType(item)

            itemView.setOnClickListener { onScriptClicked?.invoke(item) }
        }

        private fun setupIconType(item: Script) {
            val icon = if (item.isTVShow) R.drawable.ic_tv else R.drawable.ic_movie
            val color = if (item.isTVShow) R.color.serie_color else R.color.movie_color

            itemView.typeIcon.setResourceImageDrawable(icon)
            itemView.typeIconContainer.setCardBackgroundColor(itemView.context.getResourceColor(color))
        }

        private fun setupPoster(item: Script) {
            itemView.poster.loadImage(item.poster)
        }
    }

}