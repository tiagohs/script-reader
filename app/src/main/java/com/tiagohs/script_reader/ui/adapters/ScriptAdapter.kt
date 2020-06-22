package com.tiagohs.script_reader.ui.adapters

import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.Constraints
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.entities.Category
import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.helpers.extensions.*
import com.tiagohs.script_reader.ui.adapters.base.BaseAdapter
import com.tiagohs.script_reader.ui.adapters.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_script.view.*
import kotlinx.android.synthetic.main.view_category.view.*

class ScriptAdapter(
    scriptList: List<Script>
): BaseAdapter<Script, ScriptAdapter.ScriptViewHolder>(scriptList) {

    var onScriptClicked: ((script: Script) -> Unit)? = null
    var onCategoryClicked: ((category: Category) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = R.layout.adapter_script

    override fun onCreateViewHolder(viewType: Int, view: View): ScriptViewHolder = ScriptViewHolder(view)

    inner class ScriptViewHolder(itemView: View): BaseViewHolder<Script>(itemView), LayoutContainer {

        override fun bind(item: Script, position: Int) {
            super.bind(item, position)

            itemView.scriptTitle.setResourceText(item.title)
            itemView.movieYear.setResourceText(item.year)
            itemView.author.setResourceText(item.writers?.mapNotNull { it.title }?.joinToString())

            setupImage(item)
            setupCategories(item)
            setupEpisodioName(item)

            itemView.setOnClickListener { onScriptClicked?.invoke(item) }
        }

        private fun setupEpisodioName(item: Script) {
            if (item.isTVShow) {
                itemView.episodeName.setResourceText("\"${item.episode}\"")
                itemView.episodeName.show()
                return
            }

            itemView.episodeName.hide()
        }

        private fun setupImage(item: Script) {
            val icon = if (item.isTVShow) R.drawable.ic_tv else R.drawable.ic_movie

            itemView.icon.setResourceImageDrawable(icon)
        }

        private fun setupCategories(item: Script) {
            item.genres?.let {
                itemView.categoriesScrollView.visibility = View.VISIBLE

                it.forEach { genre ->
                    val view = LayoutInflater.from(itemView.context).inflate(R.layout.view_category, null, false)
                    val layoutParams = Constraints.LayoutParams(Constraints.LayoutParams.WRAP_CONTENT, Constraints.LayoutParams.WRAP_CONTENT)

                    layoutParams.setMargins(0, 0, 10.convertIntToDp(itemView.context), 0)
                    view.viewCategoryName.setResourceText(genre.title)
                    view.setOnClickListener { onCategoryClicked?.invoke(genre) }

                    view.layoutParams = layoutParams

                    itemView.categoriesContainer.addView(view)
                }
            }
        }
    }

}