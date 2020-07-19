package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
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
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

class SearchActivity :
    BaseActivity(),
    SearchView,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {

    override val layoutViewId: Int = R.layout.activity_search

    @Inject
    lateinit var presenter: SearchPresenter
    private var adapter: ScriptAdapter? = null
    private var searchView: androidx.appcompat.widget.SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent()?.inject(this)

        presenter.onBindView(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        hideLoading()

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        setupSearchViewItem(menu)

        return super.onCreateOptionsMenu(menu)
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

        recyclerView.apply {
            layoutManager =
                LinearLayoutManager(this@SearchActivity, LinearLayoutManager.VERTICAL, false)

            addItemDecoration(
                SpaceOffsetDecoration(
                    16.convertIntToDp(this@SearchActivity),
                    SpaceOffsetDecoration.TOP
                )
            )
        }
    }

    override fun loadList(list: List<Script>) {
        recyclerView.adapter = ScriptAdapter(list, LinearLayoutManager.VERTICAL).apply {
            onScriptClicked = { presentScriptDetailsScreen(it) }
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

    override fun onQueryTextChange(newText: String): Boolean {
        presenter.searchScripts(newText)

        return true
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        presenter.searchScripts(query)
        searchView?.clearFocus()

        return true
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, SearchActivity::class.java)
    }
}