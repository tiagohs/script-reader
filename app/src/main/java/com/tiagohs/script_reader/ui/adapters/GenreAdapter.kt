package com.tiagohs.script_reader.ui.adapters

import android.view.View
import com.tiagohs.helpers.enums.Genres
import com.tiagohs.helpers.extensions.hide
import com.tiagohs.helpers.extensions.setResourceImageDrawable
import com.tiagohs.helpers.extensions.setResourceText
import com.tiagohs.helpers.extensions.show
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.adapters.base.BaseAdapter
import com.tiagohs.script_reader.ui.adapters.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_genre.view.*

class GenreAdapter(
    categoryList: List<Genres>
) : BaseAdapter<Genres, GenreAdapter.GenreViewHolder>(categoryList) {

    var onGenreClicked: ((genre: Genres) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = R.layout.adapter_genre

    override fun onCreateViewHolder(viewType: Int, view: View): GenreViewHolder =
        GenreViewHolder(view)

    inner class GenreViewHolder(itemView: View) : BaseViewHolder<Genres>(itemView),
        LayoutContainer {

        override fun bind(item: Genres, position: Int) {
            super.bind(item, position)

            itemView.categoryName.setResourceText(item.title)

            setupImage(item)

            itemView.setOnClickListener { onGenreClicked?.invoke(item) }
        }

        private fun setupImage(item: Genres) {
            if (item.icon == 0) {
                itemView.categoryIcon.hide()
                return
            }

            itemView.categoryIcon.show()
            itemView.categoryIcon.setResourceImageDrawable(item.icon)
        }
    }

}