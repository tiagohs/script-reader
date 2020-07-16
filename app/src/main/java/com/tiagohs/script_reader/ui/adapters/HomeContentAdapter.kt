package com.tiagohs.script_reader.ui.adapters

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import com.tiagohs.entities.home.CategoryCell
import com.tiagohs.entities.home.HomeCell
import com.tiagohs.entities.home.LastestCell
import com.tiagohs.helpers.enums.HomeType
import com.tiagohs.helpers.extensions.convertIntToDp
import com.tiagohs.helpers.tools.ScriptSpecialPageTransformation
import com.tiagohs.helpers.tools.SpaceOffsetDecoration
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.adapters.base.BaseAdapter
import com.tiagohs.script_reader.ui.adapters.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_home_categories_cell.view.*
import kotlinx.android.synthetic.main.adapter_home_movie_special_cell.view.*

class HomeContentAdapter(
    list: List<HomeCell>
) : BaseAdapter<HomeCell, BaseViewHolder<HomeCell>>(list) {

    var onCategoryClicked: ((category: Category) -> Unit)? = null
    var onScriptClicked: ((script: Script) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = when (viewType) {
        HomeType.CATEGORIES.ordinal -> R.layout.adapter_home_categories_cell
//        HomeType.LASTEST.ordinal -> R.layout.adapter_home_movies_cell
        HomeType.LASTEST.ordinal -> R.layout.adapter_home_movie_special_cell
        else -> R.layout.adapter_home_categories_cell
    }

    override fun onCreateViewHolder(viewType: Int, view: View): BaseViewHolder<HomeCell> =
        when (viewType) {
            HomeType.CATEGORIES.ordinal -> CategoryHomeContentViewHolder(view)
            HomeType.LASTEST.ordinal -> LastestHomeContentViewHolder(view)
            else -> object : BaseViewHolder<HomeCell>(view) {}
        }

    override fun getItemViewType(position: Int): Int = list[position].type.ordinal

    inner class CategoryHomeContentViewHolder(itemView: View) : BaseViewHolder<HomeCell>(itemView),
        LayoutContainer {

        var isSetup = false

        override fun bind(item: HomeCell, position: Int) {
            super.bind(item, position)

            val categoryItem = item as? CategoryCell ?: return

            if (!isSetup) {
                itemView.recyclerView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                itemView.recyclerView.addItemDecoration(
                    SpaceOffsetDecoration(
                        6.convertIntToDp(itemView.context),
                        SpaceOffsetDecoration.LEFT
                    )
                )
                itemView.recyclerView.adapter = CategoryAdapter(categoryItem.list).apply {
                    onCategoryClicked = this@HomeContentAdapter.onCategoryClicked
                }
                isSetup = true
            }
        }
    }

    inner class LastestHomeContentViewHolder(itemView: View) : BaseViewHolder<HomeCell>(itemView),
        LayoutContainer {

        var isSetup = false

        override fun bind(item: HomeCell, position: Int) {
            super.bind(item, position)

            val lastestItem = item as? LastestCell ?: return

            itemView.scriptsRecyclerView.apply {
                clipToPadding = false
                clipChildren = false
                offscreenPageLimit = 3

                setPageTransformer(ScriptSpecialPageTransformation())

                adapter = ScriptSimpleAdapter(lastestItem.list).apply {
                    onScriptClicked = this@HomeContentAdapter.onScriptClicked
                    onCategoryClicked = this@HomeContentAdapter.onCategoryClicked
                }
            }
        }

//        override fun bind(item: HomeCell, position: Int) {
//            super.bind(item, position)
//
//            val lastestItem = item as? LastestCell ?: return
//
//            if (!isSetup) {
//                itemView.scriptsRecyclerView.layoutManager =
//                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
//                itemView.scriptsRecyclerView.addItemDecoration(
//                    SpaceOffsetDecoration(
//                        6.convertIntToDp(itemView.context),
//                        SpaceOffsetDecoration.LEFT
//                    )
//                )
//                itemView.scriptsRecyclerView.adapter = ScriptAdapter(lastestItem.list).apply {
//                    onScriptClicked = this@HomeContentAdapter.onScriptClicked
//                    onCategoryClicked = this@HomeContentAdapter.onCategoryClicked
//                }
//
//                isSetup = true
//            }
//        }
    }

}