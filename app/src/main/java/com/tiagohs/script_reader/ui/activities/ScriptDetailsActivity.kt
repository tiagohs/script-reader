package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.constraintlayout.widget.Constraints
import com.tiagohs.domain.presenter.contract.ScriptDetailsPresenter
import com.tiagohs.helpers.Constants
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.domain.views.ScriptDetailsView
import com.tiagohs.entities.Script
import com.tiagohs.helpers.enums.ScriptType
import com.tiagohs.helpers.extensions.*
import kotlinx.android.synthetic.main.activity_script_details.*
import kotlinx.android.synthetic.main.activity_script_details.loadView
import kotlinx.android.synthetic.main.activity_script_details.toolbar
import kotlinx.android.synthetic.main.view_category.view.*
import javax.inject.Inject

class ScriptDetailsActivity :
    BaseActivity(),
    ScriptDetailsView {

    override val layoutViewId: Int = R.layout.activity_script_details

    @Inject
    lateinit var presenter: ScriptDetailsPresenter

    private var scriptType: ScriptType = ScriptType.MOVIE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getApplicationComponent()?.inject(this)

        presenter.onBindView(this)
    }

    override fun onDestroy() {
        presenter.onDestroy()
        loadView.hide()

        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_script_details, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_share -> {
            presenter.onSharedClicked()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun setupArguments() {
        val script = intent.extras?.getParcelable(Constants.ARGUMENTS.SCRIPT) as? Script

        scriptType = intent.extras?.getSerializable(Constants.ARGUMENTS.SCRIPT_TYPE) as? ScriptType ?: ScriptType.MOVIE

        presenter.setArgument(script)
    }

    override fun setTitle(title: String?) {
        collapsingToolbarLayout.title = title
    }

    override fun setupContentView() {
        setupToolbar(toolbar)

        toolbar.post {
            val color = if (scriptType == ScriptType.TV_SHOW) R.color.serie_color else R.color.movie_color

            toolbar.setBackgroundColor(getResourceColor(color))
        }
    }

    override fun showScriptDetails(script: Script) {
        scriptTitle.setResourceText(script.title)
        authors.setResourceText("${script.year} - ${script.writers?.mapNotNull { it.title }?.joinToString()}")

        readScriptButtonContainer.setOnClickListener { presentReaderScreen(script) }

        setupContentType()
        setupImage(script)
        setupSynopses(script)
        setupCategoryList(script)
    }

    private fun setupContentType() {
        if (scriptType == ScriptType.TV_SHOW) {
            appBar.setResourceBackgroundColor(R.color.serie_color)
            movieHeaderContentContainer.setResourceBackgroundColor(R.color.serie_color)
            readScriptButtonContainer.setCardBackgroundColor(getResourceColor(R.color.serie_color_dark))
            return
        }

        appBar.setResourceBackgroundColor(R.color.movie_color)
        movieHeaderContentContainer.setResourceBackgroundColor(R.color.movie_color)
        readScriptButtonContainer.setCardBackgroundColor(getResourceColor(R.color.movie_color_dark))
    }

    private fun setupImage(script: Script) {
        imageView.loadImage(script.poster)
    }

    private fun setupSynopses(script: Script) {
        if (script.synopses.isNullOrEmpty()) {
            sinopse.hide()
            return
        }

        sinopse.show()
        sinopse.setResourceText(script.synopses)
    }

    private fun setupCategoryList(script: Script) {
        val genres = script.genres ?: emptyList()
        val writers = script.writers ?: emptyList()
        val list = writers.toMutableList()

        list.addAll(genres)

        if (list.isEmpty()) {
            categoriesScrollView.hide()
            categoriesContainer.hide()
            return
        }

        list.forEach { category ->
            val view = LayoutInflater.from(this).inflate(R.layout.view_category, null, false)
            val layoutParams = Constraints.LayoutParams(Constraints.LayoutParams.WRAP_CONTENT, Constraints.LayoutParams.WRAP_CONTENT)

            layoutParams.setMargins(10.convertIntToDp(this), 0, 10.convertIntToDp(this), 0)
            view.viewCategoryName.setResourceText(category.title)
            view.setOnClickListener { presentCategoryScreen(category) }

            view.layoutParams = layoutParams

            categoriesContainer.addView(view)
        }
    }

    override fun shareScript(scriptUrl: String?) {
        shareContent(getString(R.string.share_message, scriptUrl), getResourceString(R.string.action_share))
    }

    override fun showLoading() {
        movieHeaderContentContainer.hide()
        readScriptButtonContainer.hide()
        pageContentListContainer.hide()
        imageContainer.hide()

        loadView.show()
    }

    override fun hideLoading() {
        movieHeaderContentContainer.show()
        readScriptButtonContainer.show()
        pageContentListContainer.show()
        imageContainer.show()

        loadView.hide()
    }

    companion object {
        fun newIntent(context: Context?, script: Script): Intent = Intent(context, ScriptDetailsActivity::class.java).apply {
            putExtra(Constants.ARGUMENTS.SCRIPT, script)
            putExtra(Constants.ARGUMENTS.SCRIPT_TYPE, script.scriptType)
        }
    }
}
