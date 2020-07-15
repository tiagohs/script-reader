package com.tiagohs.script_reader.ui.adapters

import android.view.View
import com.tiagohs.entities.Category
import com.tiagohs.helpers.extensions.setResourceImageDrawable
import com.tiagohs.helpers.extensions.setResourceText
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.adapters.base.BaseAdapter
import com.tiagohs.script_reader.ui.adapters.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_category.view.*

class CategoryAdapter(
    categoryList: List<Category>
): BaseAdapter<Category, CategoryAdapter.CategoryViewHolder>(categoryList) {

    var onCategoryClicked: ((category: Category) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = R.layout.adapter_category

    override fun onCreateViewHolder(viewType: Int, view: View): CategoryViewHolder = CategoryViewHolder(view)

    inner class CategoryViewHolder(itemView: View): BaseViewHolder<Category>(itemView), LayoutContainer {

        override fun bind(item: Category, position: Int) {
            super.bind(item, position)

            // itemView.categoryNameIcon.setResourceText(item.title?.firstOrNull().toString())
            itemView.categoryName.setResourceText(item.title)

            itemView.setOnClickListener { onCategoryClicked?.invoke(item) }

//            val icon = item.icon ?: return

            if (item.title == "Animation") {
                itemView.categoryIcon.setResourceImageDrawable(R.drawable.ic_genre_animation)
            }

            if (item.title == "Comedy") {
                itemView.categoryIcon.setResourceImageDrawable(R.drawable.ic_genre_comedy)
            }

        }
    }

}