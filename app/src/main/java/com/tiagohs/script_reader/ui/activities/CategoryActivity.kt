package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiagohs.script_reader.Constants
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.entities.Category
import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.helpers.extensions.convertIntToDp
import com.tiagohs.script_reader.presenter.contract.CategoryPresenter
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.script_reader.ui.adapters.ScriptAdapter
import com.tiagohs.script_reader.ui.custom.SpaceOffsetDecoration
import com.tiagohs.script_reader.ui.views.CategoryView
import kotlinx.android.synthetic.main.activity_category.*
import javax.inject.Inject

class CategoryActivity :
    BaseActivity(),
    CategoryView {

    override val layoutViewId: Int = R.layout.activity_category

    @Inject
    lateinit var categoryPresenter: CategoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent()?.inject(this)

        categoryPresenter.onBindView(this)
    }

    override fun onDestroy() {
        categoryPresenter.onDestroy()
        loadView.hide()

        super.onDestroy()
    }

    override fun setupAlguments() {
        val category = intent.extras?.getParcelable(Constants.ARGUMENTS.CATEGORY) as? Category ?: return

        categoryPresenter.setArguments(category)
    }

    override fun setTitle(title: String?) {
        toolbar.post {
            setScreenTitle(title)
        }
    }

    override fun setupContentView() {
        setupToolbar(toolbar)
    }

    override fun loadList(list: List<Script>) {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CategoryActivity, LinearLayoutManager.VERTICAL, false)
            recyclerView.adapter = ScriptAdapter(list)

            addItemDecoration(SpaceOffsetDecoration(16.convertIntToDp(context), SpaceOffsetDecoration.TOP))
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
