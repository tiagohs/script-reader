package com.tiagohs.script_reader.ui.adapters

import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import com.tiagohs.entities.home.*
import com.tiagohs.helpers.enums.GenresEnum
import com.tiagohs.helpers.enums.HomeTypeEnum
import com.tiagohs.helpers.extensions.*
import com.tiagohs.helpers.tools.SpaceOffsetDecoration
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.adapters.base.BaseAdapter
import com.tiagohs.script_reader.ui.adapters.base.BaseViewHolder
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.adapter_home_categories_cell.view.*
import kotlinx.android.synthetic.main.adapter_home_genres_cell.view.*
import kotlinx.android.synthetic.main.adapter_home_list_default_cell.view.*
import kotlinx.android.synthetic.main.adapter_home_list_special_cell.view.*

class HomeContentAdapter(
    list: List<HomeCell>,
    val activity: FragmentActivity
) : BaseAdapter<HomeCell, BaseViewHolder<HomeCell>>(list) {

    var onGenreClicked: ((genre: GenresEnum) -> Unit)? = null
    var onCategoryClicked: ((category: Category) -> Unit)? = null
    var onScriptClicked: ((script: Script) -> Unit)? = null

    override fun getLayoutResId(viewType: Int): Int = when (viewType) {
        HomeTypeEnum.GENRES.ordinal -> R.layout.adapter_home_genres_cell
        HomeTypeEnum.CATEGORIES.ordinal -> R.layout.adapter_home_categories_cell
        HomeTypeEnum.LIST_DEFAULT.ordinal -> R.layout.adapter_home_list_default_cell
        HomeTypeEnum.LIST_SPECIAL.ordinal -> R.layout.adapter_home_list_special_cell
        else -> R.layout.adapter_home_empty_cell
    }

    override fun onCreateViewHolder(viewType: Int, view: View): BaseViewHolder<HomeCell> =
        when (viewType) {
            HomeTypeEnum.GENRES.ordinal -> GenresHomeContentViewHolder(view)
            HomeTypeEnum.CATEGORIES.ordinal -> CategoryHomeContentViewHolder(view)
            HomeTypeEnum.LIST_DEFAULT.ordinal -> DefaultListHomeContentViewHolder(view)
            HomeTypeEnum.LIST_SPECIAL.ordinal -> SpecialListHomeContentViewHolder(view)
            else -> object : BaseViewHolder<HomeCell>(view) {}
        }

    override fun getItemViewType(position: Int): Int = list[position].type.ordinal

    inner class GenresHomeContentViewHolder(itemView: View) : BaseViewHolder<HomeCell>(itemView),
        LayoutContainer {

        private var isSetup = false

        override fun bind(item: HomeCell, position: Int) {
            super.bind(item, position)

            val genreCell = item as? GenreCell ?: return

            if (!isSetup) {
                itemView.genresRecyclerView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                itemView.genresRecyclerView.addItemDecoration(
                    SpaceOffsetDecoration(
                        6.convertIntToDp(itemView.context),
                        SpaceOffsetDecoration.LEFT
                    )
                )
                itemView.genresRecyclerView.adapter = GenreAdapter(genreCell.genres).apply {
                    onGenreClicked = this@HomeContentAdapter.onGenreClicked
                }
                isSetup = true
            }
        }
    }

    inner class CategoryHomeContentViewHolder(itemView: View) : BaseViewHolder<HomeCell>(itemView),
        LayoutContainer {

        private var isSetup = false

        override fun bind(item: HomeCell, position: Int) {
            super.bind(item, position)

            val categoryCell = item as? CategoryCell ?: return

            if (!isSetup) {
                itemView.categoriesRecyclerView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                itemView.categoriesRecyclerView.addItemDecoration(
                    SpaceOffsetDecoration(
                        6.convertIntToDp(itemView.context),
                        SpaceOffsetDecoration.LEFT
                    )
                )
                itemView.categoriesRecyclerView.adapter = CategoryAdapter(categoryCell.list).apply {
                    onCategoryClicked = this@HomeContentAdapter.onCategoryClicked
                }
                isSetup = true
            }
        }
    }

    inner class SpecialListHomeContentViewHolder(itemView: View) : BaseViewHolder<HomeCell>(itemView),
        LayoutContainer {

        private var isSetup = false

        override fun bind(item: HomeCell, position: Int) {
            super.bind(item, position)

            val specialList = item as? ListSpecialCell ?: return

            if (!isSetup) {
                val config = specialList.config

                itemView.scriptsSpecialListTitle.setResourceTextColor(config.textColor)
                itemView.scriptsSpecialListSubtitle.setResourceTextColor(config.textColor)
                itemView.scriptsSpecialListContainer.setResourceBackgroundColor(config.backgroundColor)

                itemView.scriptsSpecialListTitle.setResourceText(specialList.title)
                itemView.scriptsSpecialListSubtitle.setResourceText(specialList.subtitle)

                itemView.imageSpecial.setResourceImageDrawable(config.image)

                itemView.scriptsSpecialListRecyclerView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                itemView.scriptsSpecialListRecyclerView.addItemDecoration(
                    SpaceOffsetDecoration(
                        activity.getScreenWidth() / 2,
                        SpaceOffsetDecoration.LEFT
                    )
                )

                itemView.headerViewClickable.setOnClickListener {
                    onCategoryClicked?.invoke(
                        Category(
                            title = itemView.context.getResourceString(specialList.title),
                            url = config.url
                        )
                    )
                }

                itemView.scriptsSpecialListRecyclerView.adapter = ScriptSimpleAdapter(specialList.list).apply {
                    onScriptClicked = this@HomeContentAdapter.onScriptClicked
                    onCategoryClicked = this@HomeContentAdapter.onCategoryClicked
                }
                itemView.scriptsSpecialListRecyclerView.addOnScrollListener(object :
                    RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        val adapter = recyclerView.adapter ?: return

                        if (adapter.itemCount != 0) {
                            val lastVisibleItemPosition =
                                (recyclerView.layoutManager as? LinearLayoutManager)?.findLastCompletelyVisibleItemPosition()
                                    ?: return
                            if (lastVisibleItemPosition != RecyclerView.NO_POSITION && lastVisibleItemPosition == 0) {
                                itemView.backgroundColor
                                    .animate()
                                    .alpha(0f)
                                    .setDuration(400)
                                    .setListener(null)
                            } else if (lastVisibleItemPosition == 1) {
                                itemView.backgroundColor
                                    .animate()
                                    .alpha(0.9f)
                                    .setDuration(400)
                                    .setListener(null)
                            }
                        }
                    }
                })

                isSetup = true
            }
        }
    }

    inner class DefaultListHomeContentViewHolder(itemView: View) : BaseViewHolder<HomeCell>(itemView),
        LayoutContainer {

        private var isSetup = false

        override fun bind(item: HomeCell, position: Int) {
            super.bind(item, position)

            val defaultList = item as? ListDefaultCell ?: return

            if (!isSetup) {
                itemView.listDefaultTitle.setResourceText(defaultList.title)

                itemView.listDefaultRecyclerView.layoutManager =
                    LinearLayoutManager(itemView.context, LinearLayoutManager.HORIZONTAL, false)
                itemView.listDefaultRecyclerView.addItemDecoration(
                    SpaceOffsetDecoration(
                        6.convertIntToDp(itemView.context),
                        SpaceOffsetDecoration.LEFT
                    )
                )
                itemView.listDefaultRecyclerView.adapter = ScriptAdapter(defaultList.list, orientation = LinearLayoutManager.HORIZONTAL).apply {
                    onCategoryClicked = this@HomeContentAdapter.onCategoryClicked
                    onScriptClicked = this@HomeContentAdapter.onScriptClicked
                }
                isSetup = true
            }
        }
    }
}