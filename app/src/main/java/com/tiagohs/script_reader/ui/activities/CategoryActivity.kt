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
import kotlinx.android.synthetic.main.activity_category.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

class CategoryActivity :
    BaseActivity<CategoryPresenter>(),
    CategoryView {

    override val presenter: CategoryPresenter by inject { parametersOf(this) }

    override val layoutViewId: Int = R.layout.activity_category

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

    override fun loadList(list: List<Script>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CategoryActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = ScriptAdapter(list, LinearLayoutManager.VERTICAL).apply {
                onScriptClicked = { presentScriptDetailsScreen(it) }
            }

            addItemDecoration(
                SpaceOffsetDecoration(
                    16.convertIntToDp(
                        context
                    ), SpaceOffsetDecoration.TOP
                )
            )
        }
    }

    override fun showLoading() {
        loadView.show()
    }

    override fun hideLoading() {
        loadView.hide()
    }

    companion object {
        fun newIntent(context: Context?, category: Category): Intent = Intent(context, CategoryActivity::class.java).apply {
            putExtra(Constants.ARGUMENTS.CATEGORY, category)
        }
    }
}
