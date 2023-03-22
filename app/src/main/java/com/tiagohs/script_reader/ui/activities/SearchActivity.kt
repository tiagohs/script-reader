package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiagohs.domain.presenter.contract.ScriptDetailsPresenter
import com.tiagohs.domain.presenter.contract.SearchPresenter
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.script_reader.ui.adapters.ScriptAdapter
import com.tiagohs.helpers.tools.SpaceOffsetDecoration
import com.tiagohs.domain.views.SearchView
import com.tiagohs.entities.Script
import com.tiagohs.helpers.extensions.convertIntToDp
import com.tiagohs.helpers.extensions.hide
import com.tiagohs.helpers.extensions.show
import com.tiagohs.helpers.tools.PaginationScrollListener
import com.tiagohs.helpers.utils.hideKeyboardFrom
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

class SearchActivity :
    BaseActivity<SearchPresenter>(),
    SearchView,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    override val presenter: SearchPresenter by inject { parametersOf(this) }

    override val layoutViewId: Int = R.layout.activity_search

    private var searchView: androidx.appcompat.widget.SearchView? = null

    private var isLoading = false
    private var isLastPage = false
    private var currentPage = PAGE_START
    private var query: String = ""

    private var adapter: ScriptAdapter? = null

    override fun onDestroy() {
        hideLoading()
        hideKeyboardFrom(this, searchView)

        super.onDestroy()
    }

    private fun setupSearchViewItem(menu: Menu) {
        this.searchView =
            menu.findItem(R.id.action_search)?.actionView as? androidx.appcompat.widget.SearchView

        searchView?.apply {
            findViewById<View>(androidx.appcompat.R.id.search_plate)?.run { background = null }

            maxWidth = android.R.attr.width
            queryHint = getString(R.string.query_hint_message)
            isIconified = false

            setOnQueryTextListener(this@SearchActivity)

            val inputMethod =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return
            inputMethod.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        }
    }



    override fun setupContentView() {
        setupToolbar(
            toolbar,
            displayShowTitleEnabled = false
        )
        setupSearchViewItem(toolbar.menu)

        val linearLayoutManager = LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)
        recyclerView.apply {
            layoutManager = linearLayoutManager

            addItemDecoration(
                SpaceOffsetDecoration(
                    16.convertIntToDp(this@SearchActivity),
                    SpaceOffsetDecoration.TOP
                )
            )

            addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
                override val isLastPage: Boolean
                    get() = this@SearchActivity.isLastPage
                override val isLoading: Boolean
                    get() = this@SearchActivity.isLoading

                override fun loadMoreItems() {
                    this@SearchActivity.isLoading = true

                    presenter.searchScripts(query, ++currentPage, isFirstPage = false)
                }
            })
        }
    }

    override fun loadList(list: List<Script>, isLastPage: Boolean) {
        this.adapter = ScriptAdapter(list, LinearLayoutManager.VERTICAL).apply {
            onScriptClicked = { presentScriptDetailsScreen(it) }
        }

        recyclerView.adapter = this.adapter

        if (!isLastPage) {
            adapter?.addLoadingFooter()
        }
    }

    override fun loadListMore(list: List<Script>, isLastPage: Boolean) {
        this.isLastPage = isLastPage

        isLoading = false

        adapter?.removeLoadingFooter()
        adapter?.addAll(list)

        if (!isLastPage) {
            adapter?.addLoadingFooter()
        }
    }

    override fun showLoading() {
        loadView.show()
    }

    override fun hideLoading() {
        loadView.hide()
    }

    override fun showRecyclerView() {
        recyclerView.show()
    }

    override fun hideRecyclerView() {
        recyclerView.hide()
    }

    override fun showEmptyContainer() {

    }

    override fun hideEmptyContainer() {

    }

    override fun onQueryTextChange(newText: String): Boolean {
        this.query = newText
        this.isLoading = false
        this.isLastPage = false
        this.currentPage = PAGE_START

        presenter.searchScripts(newText, currentPage, isFirstPage = true)

        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        this.query = query
        this.isLoading = false
        this.isLastPage = false
        this.currentPage = PAGE_START

        presenter.searchScripts(query, currentPage, isFirstPage = true)
        searchView?.clearFocus()
        hideKeyboardFrom(this, searchView)

        return true
    }

    companion object {
        private const val PAGE_START = 1

        fun newIntent(context: Context): Intent = Intent(context, SearchActivity::class.java)
    }
}