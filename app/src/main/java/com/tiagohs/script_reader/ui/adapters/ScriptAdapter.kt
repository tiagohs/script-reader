package com.tiagohs.script_reader.ui.adapters

import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.Constraints
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import com.tiagohs.helpers.enums.ScriptType
import com.tiagohs.helpers.extensions.*
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.adapters.base.BaseAdapter
import com.tiagohs.script_reader.ui.adapters.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_script_horizontal.view.*
import kotlinx.android.synthetic.main.view_category.view.*

class ScriptAdapter(
    scriptList: List<Script>,
    val orientation: Int
): BaseAdapter<Script, ScriptAdapter.ScriptViewHolder>(scriptList) {

    var onScriptClicked: ((script: Script) -> Unit)? = null
    var onCategoryClicked: ((category: Category) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = R.layout.adapter_script_horizontal

    override fun onCreateViewHolder(viewType: Int, view: View): ScriptViewHolder = ScriptViewHolder(view)

    inner class ScriptViewHolder(itemView: View): BaseViewHolder<Script>(itemView), LayoutContainer {

        init {
            if (orientation == LinearLayoutManager.HORIZONTAL) {
                itemView.scriptContainer.layoutParams = Constraints.LayoutParams(itemView.context.resources.getDimensionPixelOffset(R.dimen.script_container_width), Constraints.LayoutParams.WRAP_CONTENT)
            } else {
                itemView.scriptContainer.layoutParams = Constraints.LayoutParams(Constraints.LayoutParams.MATCH_PARENT, Constraints.LayoutParams.WRAP_CONTENT)
            }
        }

        override fun bind(item: Script, position: Int) {
            super.bind(item, position)

            itemView.scriptTitle.setResourceText(item.title)
            itemView.movieYear.setResourceText(item.year)
            itemView.author.setResourceText(item.writers?.mapNotNull { it.title }?.joinToString())

            setupIconType(item)
            setupPoster(item)
            setupCategories(item)
            setupEpisodeName(item)

            itemView.setOnClickListener { onScriptClicked?.invoke(item) }
        }

        private fun setupEpisodeName(item: Script) {
            if (item.scriptType == ScriptType.TV_SHOW) {
                itemView.episodeName.setResourceText("\"${item.episode}\"")
                return
            }
        }

        private fun setupIconType(item: Script) {
            val icon = if (item.scriptType == ScriptType.TV_SHOW) R.drawable.ic_tv else R.drawable.ic_movie
            val color = if (item.scriptType == ScriptType.TV_SHOW) R.color.serie_color else R.color.movie_color

            itemView.typeIcon.setResourceImageDrawable(icon)
            itemView.typeIconContainer.setCardBackgroundColor(itemView.context.getResourceColor(color))
        }

        private fun setupPoster(item: Script) {
            itemView.poster.loadImage(item.poster)
        }

        private fun setupCategories(item: Script) {
            val genres = item.genres?.filter { !it.title.isNullOrEmpty() } ?: emptyList()

            if (genres.isNotEmpty()) {
                itemView.categoriesScrollView.show()

                genres.take(2).forEach { genre ->
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