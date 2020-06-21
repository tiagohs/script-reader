package com.tiagohs.script_reader.ui.activities.base

import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.tiagohs.script_reader.App
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.dagger.AppComponent
import com.tiagohs.script_reader.helpers.extensions.getResourceColor
import com.tiagohs.script_reader.helpers.extensions.toast
import com.tiagohs.script_reader.helpers.utils.ServerUtils

abstract class BaseActivity : AppCompatActivity() {

    abstract val layoutViewId : Int
    open var menuLayoutId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutViewId)
    }

    /*fun getConfiguratedAd(adView: AdView) {
        adView.loadAd(AdRequest.Builder().build())
    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (menuLayoutId != 0)
            menuInflater.inflate(menuLayoutId, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }

            else -> return false
        }
    }

    fun openUrl(url: String?) {

        if (!url.isNullOrEmpty()) {
            try {
                val urlUri = Uri.parse(url)
                val intent = CustomTabsIntent.Builder()
                        .setToolbarColor(getResourceColor(R.color.colorPrimary))
                        .setShowTitle(true)
                        .build()
                intent.launchUrl(this, urlUri)
            } catch (e: Exception) {
                toast(e.message)
            }
        }
    }

    fun getApplicationComponent(): AppComponent? {
        val application = application ?: return null

        return (application as App).appComponent
    }


    fun isInternetConnected(): Boolean {
        return ServerUtils.isNetworkConnected(this)
    }

    fun isAdded(): Boolean {
        return !isDestroyed
    }

    fun setScreenTitle(title: String?) {
        supportActionBar?.title = title
    }

    fun setScreenSubtitle(title: String?) {
        supportActionBar?.subtitle = title
    }

    open fun onError(ex: Throwable?, message: Int) {

        val finalMessage = if (message == 0) {
            R.string.unknown_error
        } else {
            message
        }

        onError(ex, finalMessage)
    }

    open fun onError(ex: Throwable?, message: String) {
        toast(message)
    }

}