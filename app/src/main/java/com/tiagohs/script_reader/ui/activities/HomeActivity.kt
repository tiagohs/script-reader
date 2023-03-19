package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiagohs.domain.presenter.contract.HomePresenter
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.script_reader.ui.adapters.HomeContentAdapter
import com.tiagohs.domain.views.HomeView
import com.tiagohs.entities.Category
import com.tiagohs.entities.home.HomeCell
import kotlinx.android.synthetic.main.activity_home.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

class HomeActivity :
    BaseActivity<HomePresenter>(),
    HomeView {

    override val presenter: HomePresenter by inject { parametersOf(this) }

    override val layoutViewId: Int = R.layout.activity_home

    override fun onDestroy() {
        loadView.hide()

        super.onDestroy()
    }

    override fun onMenuItemClickListener(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_search -> {
            startActivity(SearchActivity.newIntent(this))
            true
        }
        else -> false
    }

    override fun setupContentView() {
        setupToolbar(
            toolbar,
            displayHomeAsUpEnabled = false,
            displayShowTitleEnabled = false,
            displayShowHomeEnabled = false
        )
    }

    override fun loadHomeContent(homeContent: List<HomeCell>) {
        homeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        homeList.adapter = HomeContentAdapter(homeContent, this).apply {
            onCategoryClicked = { presentCategoryScreen(it) }
            onGenreClicked = { presentCategoryScreen(Category.from(it)) }
            onScriptClicked = { presentScriptDetailsScreen(it) }
        }
    }

    override fun showLoading() {
        loadView.show()
    }

    override fun hideLoading() {
        loadView.hide()
    }

    companion object {
        fun newIntent(context: Context): Intent = Intent(context, HomeActivity::class.java)
    }

}
