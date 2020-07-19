package com.tiagohs.script_reader.ui.adapters

import android.view.View
import com.tiagohs.helpers.enums.GenresEnum
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
    categoryList: List<GenresEnum>
) : BaseAdapter<GenresEnum, GenreAdapter.GenreViewHolder>(categoryList) {

    var onGenreClicked: ((genre: GenresEnum) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = R.layout.adapter_genre

    override fun onCreateViewHolder(viewType: Int, view: View): GenreViewHolder =
        GenreViewHolder(view)

    inner class GenreViewHolder(itemView: View) : BaseViewHolder<GenresEnum>(itemView),
        LayoutContainer {

        override fun bind(item: GenresEnum, position: Int) {
            super.bind(item, position)

            itemView.genreName.setResourceText(item.title)

            setupImage(item)

            itemView.setOnClickListener { onGenreClicked?.invoke(item) }
        }

        private fun setupImage(item: GenresEnum) {
            if (item.icon == 0) {
                itemView.genreIcon.hide()
                return
            }

            itemView.genreIcon.show()
            itemView.genreIcon.setResourceImageDrawable(item.icon)
        }
    }

}