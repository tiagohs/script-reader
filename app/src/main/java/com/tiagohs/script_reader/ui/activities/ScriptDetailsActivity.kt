package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import androidx.constraintlayout.widget.Constraints
import androidx.core.view.isEmpty
import androidx.recyclerview.widget.LinearLayoutManager
import com.tiagohs.domain.presenter.contract.ScriptDetailsPresenter
import com.tiagohs.helpers.Constants
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.domain.views.ScriptDetailsView
import com.tiagohs.entities.Script
import com.tiagohs.helpers.enums.ScriptType
import com.tiagohs.helpers.extensions.*
import com.tiagohs.script_reader.ui.adapters.ScriptAdapter
import kotlinx.android.synthetic.main.activity_script_details.*
import kotlinx.android.synthetic.main.activity_script_details.loadView
import kotlinx.android.synthetic.main.activity_script_details.toolbar
import kotlinx.android.synthetic.main.view_category.view.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

class ScriptDetailsActivity :
    BaseActivity<ScriptDetailsPresenter>(),
    ScriptDetailsView {

    override val presenter: ScriptDetailsPresenter by inject { parametersOf(this) }

    override val layoutViewId: Int = R.layout.activity_script_details

    private var scriptType: ScriptType = ScriptType.MOVIE

    override fun onDestroy() {
        loadView.hide()

        super.onDestroy()
    }

    override fun onMenuItemClickListener(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_share -> {
            presenter.onSharedClicked()
            true
        }
        else -> false
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
    }

    override fun showScriptDetails(script: Script) {
        scriptTitle.setResourceText(script.title)
        authors.setResourceText("${script.year} - ${script.writers?.mapNotNull { it.title }?.joinToString()}")

        readScriptButton.setOnClickListener { presentReaderScreen(script) }

        setupContentType()
        setupImage(script)
        setupSynopses(script)
        setupCategoryList(script)
        setupSeriesInfo(script)
        setupRelatedScripts(script)

    }

    private fun setupRelatedScripts(script: Script) {
        if (script.releatedScripts.isEmpty()) {
            relatedTitle.hide()
            relatedRecyclerView.hide()
            return
        }

        relatedTitle.show()
        relatedRecyclerView.apply {
            show()

            layoutManager = LinearLayoutManager(this@ScriptDetailsActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = ScriptAdapter(script.releatedScripts, LinearLayoutManager.HORIZONTAL).apply {
                onScriptClicked = { presentScriptDetailsScreen(it) }
            }
        }
    }

    private fun setupContentType() {
        toolbar.setNavigationIconTint(getResourceColor(R.color.md_white_1000))

        if (scriptType == ScriptType.TV_SHOW) {
            appBar.setResourceBackgroundColor(R.color.serie_color)
            movieHeaderBackgroundContainer.setResourceBackgroundColor(R.color.serie_color)
            imageView.setResourceBackgroundColor(R.color.serie_color_dark)
            readScriptButton.setResourceBackgroundColor(R.color.serie_color_dark)
            seriesContent.show()
            return
        }

        appBar.setResourceBackgroundColor(R.color.movie_color)
        movieHeaderBackgroundContainer.setResourceBackgroundColor(R.color.movie_color)
        imageView.setResourceBackgroundColor(R.color.movie_color_dark)
        readScriptButton.setResourceBackgroundColor(R.color.movie_color_dark)
        seriesContent.hide()
    }

    private fun setupImage(script: Script) {
        imageView.loadImage(script.poster)
        backgroundImage.loadImage(script.poster)
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

    private fun setupSeriesInfo(script: Script) {
        season_episode.setResourceText(script.season)
        episodeName.setResourceText(script.episode)
    }

    override fun shareScript(scriptUrl: String?) {
        shareContent(getString(R.string.share_message, scriptUrl), getResourceString(R.string.action_share))
    }

    override fun showLoading() {
        readScriptButton.hide()
        placeholder_sinopseLabel.hide()
        relatedTitle.hide()
        movieHeaderContentContainer.hide()
        readScriptButton.hide()
        imageContainer.hide()

        loadView.show()
    }

    override fun hideLoading() {
        readScriptButton.show()
        placeholder_sinopseLabel.show()
        relatedTitle.show()
        movieHeaderContentContainer.show()
        readScriptButton.show()
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
