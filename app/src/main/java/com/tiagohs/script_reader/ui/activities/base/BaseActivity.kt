package com.tiagohs.script_reader.ui.activities.base

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.browser.customtabs.CustomTabsIntent
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.MaterialToolbar
import com.tiagohs.script_reader.App
import com.tiagohs.script_reader.R
import com.tiagohs.components.alert_snackbar.enums.MessageType
import com.tiagohs.components.alert_snackbar.AlertSnackBar
import com.tiagohs.domain.presenter.base.IPresenter
import com.tiagohs.domain.views.base.IView
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import com.tiagohs.helpers.extensions.getResourceColor
import com.tiagohs.helpers.extensions.getResourceDrawable
import com.tiagohs.helpers.extensions.toast
import com.tiagohs.helpers.utils.ServerUtils
import com.tiagohs.script_reader.ui.activities.*

abstract class BaseActivity<T: IPresenter> : AppCompatActivity(), IView {

    abstract val layoutViewId : Int
    open var menuLayoutId: Int = 0

    var alertSnackBar: AlertSnackBar? = null

    protected abstract val presenter: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutViewId)

        presenter.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
    }

    /*fun getConfiguratedAd(adView: AdView) {
        adView.loadAd(AdRequest.Builder().build())
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menuLayoutId != 0)
            menuInflater.inflate(menuLayoutId, menu)
        return true
    }

    fun openUrl(url: String?) {

        if (!url.isNullOrEmpty()) {
            try {
                val urlUri = Uri.parse(url)
                val intent = CustomTabsIntent.Builder()
                        .setShowTitle(true)
                        .build()
                intent.launchUrl(this, urlUri)
            } catch (e: Exception) {
                toast(e.message)
            }
        }
    }

    open fun setupToolbar(toolbar: MaterialToolbar,
                          title: Int? = null,
                          displayHomeAsUpEnabled: Boolean = true,
                          displayShowHomeEnabled: Boolean = true,
                          displayShowTitleEnabled: Boolean = true) {

        if (displayShowTitleEnabled) {
            title?.let { toolbar.title = getString(it) }
        }

        if (displayHomeAsUpEnabled) {
            toolbar.navigationIcon = getResourceDrawable(R.drawable.baseline_arrow_back_24)
            toolbar.setNavigationOnClickListener {
                super.onBackPressed()
            }
        }

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    true
                }
                R.id.action_search -> {
                    startActivity(SearchActivity.newIntent(this))
                    true
                }
                R.id.action_about -> {
                    startActivity(AboutActivity.newIntent(this))
                    true
                }
                else -> onMenuItemClickListener(item)
            }
        }
    }

    open fun onMenuItemClickListener(item: MenuItem): Boolean {
        return false
    }

    override fun isInternetConnected(): Boolean {
        return ServerUtils.isNetworkConnected(this)
    }

    override fun isAdded(): Boolean {
        return !isDestroyed
    }

    open fun onError(ex: Throwable?, message: Int) {

        val finalMessage = if (message == 0) {
            R.string.unknown_error
        } else {
            message
        }

        onError(ex, finalMessage)
    }

    override fun showMessage(ex: Throwable?, messageType: com.tiagohs.components.alert_snackbar.enums.MessageType, message: Int, onTryAgainClicked: (() -> Unit)?) {
        findViewById<CoordinatorLayout>(R.id.coordinator)?.let {
            var onTryAgain:  (() -> Unit)? = null

            if (onTryAgainClicked != null) {
                onTryAgain = {
                    alertSnackBar?.dismiss()

                    onTryAgainClicked.invoke()
                }
            }

            alertSnackBar = com.tiagohs.components.alert_snackbar.AlertSnackBar.make(it, messageType, message, onTryAgainClicked = onTryAgain)
        }

    }

    override fun showMessage(ex: Throwable?, messageType: com.tiagohs.components.alert_snackbar.enums.MessageType, message: String, onTryAgainClicked: (() -> Unit)?) {

    }

    // SCREENS


    fun presentCategoryScreen(category: Category) {
        startActivity(CategoryActivity.newIntent(this, category))
    }

    fun presentReaderScreen(script: Script) {
        startActivity(ReaderActivity.newIntent(this, script))
    }

    fun presentScriptDetailsScreen(script: Script) {
        startActivity(ScriptDetailsActivity.newIntent(this, script))
    }

    fun presentSearchScreen() {
        startActivity(SearchActivity.newIntent(this))
    }
}