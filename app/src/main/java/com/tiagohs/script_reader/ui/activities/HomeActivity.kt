package com.tiagohs.script_reader.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.entities.Category
import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.presenter.contract.HomePresenter
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.script_reader.ui.adapters.HomeContentAdapter
import com.tiagohs.script_reader.ui.views.HomeView
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity :
    BaseActivity(),
    HomeView {

    override val layoutViewId: Int = R.layout.activity_home

    @Inject
    lateinit var homePresenter: HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent()?.inject(this)

        homePresenter.onBindView(this)
    }

    override fun onDestroy() {
        homePresenter.onDestroy()
        loadView.hide()

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_search -> {
            startActivity(SearchActivity.newIntent(this))
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun setupContentView() {
        setupToolbar(
            toolbar,
            displayHomeAsUpEnabled = false,
            displayShowTitleEnabled = true,
            displayShowHomeEnabled = false
        )
    }

    override fun loadHomeContent(homeContent: List<HomeCell>) {
        homeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        homeList.adapter = HomeContentAdapter(homeContent).apply {
            onCategoryClicked = { presentCategoryScreen(it) }
            onScriptClicked = { presentReaderScreen(it) }
        }
    }

    override fun showLoading() {
        loadView.show()
    }

    override fun hideLoading() {
        loadView.hide()
    }

}
