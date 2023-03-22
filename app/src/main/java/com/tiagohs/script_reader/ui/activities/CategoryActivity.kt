package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiagohs.domain.presenter.contract.CategoryPresenter
import com.tiagohs.domain.presenter.contract.HomePresenter
import com.tiagohs.helpers.Constants
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.script_reader.ui.adapters.ScriptAdapter
import com.tiagohs.helpers.tools.SpaceOffsetDecoration
import com.tiagohs.domain.views.CategoryView
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import com.tiagohs.helpers.extensions.convertIntToDp
import com.tiagohs.helpers.tools.PaginationScrollListener
import kotlinx.android.synthetic.main.activity_category.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

class CategoryActivity :
    BaseActivity<CategoryPresenter>(),
    CategoryView {

    override val presenter: CategoryPresenter by inject { parametersOf(this) }

    override val layoutViewId: Int = R.layout.activity_category

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = PAGE_START

    private var scriptAdapter: ScriptAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter.fetchScripts(currentPage, isFirstPage = true)
    }

    override fun onDestroy() {
        loadView.hide()

        super.onDestroy()
    }

    override fun setupAlguments() {
        val category = intent.extras?.getParcelable(Constants.ARGUMENTS.CATEGORY) as? Category
            ?: return

        presenter.setArguments(category)
    }

    override fun setTitle(title: String?) {
        toolbar.title = title
    }

    override fun setupContentView() {
        setupToolbar(toolbar)
    }

    override fun loadList(list: List<Script>, isLastPage: Boolean) {
        this.isLastPage = isLastPage

        val linearLayoutManager = LinearLayoutManager(this@CategoryActivity, LinearLayoutManager.VERTICAL, false)
        scriptAdapter = ScriptAdapter(list, LinearLayoutManager.VERTICAL).apply {
            onScriptClicked = { presentScriptDetailsScreen(it) }
        }

        recyclerView.apply {
            layoutManager = linearLayoutManager
            recyclerView.adapter = scriptAdapter

            addItemDecoration(
                SpaceOffsetDecoration(
                    16.convertIntToDp(
                        context
                    ), SpaceOffsetDecoration.TOP
                )
            )

            addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                override val isLastPage: Boolean
                    get() = this@CategoryActivity.isLastPage
                override val isLoading: Boolean
                    get() = this@CategoryActivity.isLoading

                override fun loadMoreItems() {
                    this@CategoryActivity.isLoading = true

                    presenter.fetchScripts(++currentPage, isFirstPage = false)
                }
            })

            if (!isLastPage) {
                scriptAdapter?.addLoadingFooter()
            }
        }
    }

    override fun loadListMore(list: List<Script>, isLastPage: Boolean) {
        this.isLastPage = isLastPage

        isLoading = false

        scriptAdapter?.removeLoadingFooter()
        scriptAdapter?.addAll(list)

        if (!isLastPage) {
            scriptAdapter?.addLoadingFooter()
        }
    }

    override fun showEmptyContainer() {

    }

    override fun hideEmptyContainer() {

    }



    override fun showLoading() {
        loadView.show()
    }

    override fun hideLoading() {
        loadView.hide()
    }

    companion object {
        private const val PAGE_START = 1

        fun newIntent(context: Context?, category: Category): Intent = Intent(context, CategoryActivity::class.java).apply {
            putExtra(Constants.ARGUMENTS.CATEGORY, category)
        }
    }
}
