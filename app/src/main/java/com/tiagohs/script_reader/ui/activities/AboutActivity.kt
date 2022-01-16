package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tiagohs.helpers.extensions.getResourceString
import com.tiagohs.script_reader.BuildConfig
import com.tiagohs.script_reader.R
import mehdi.sakout.aboutpage.AboutPage
import mehdi.sakout.aboutpage.Element

class AboutActivity :
    AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setupContentView())

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getResourceString(R.string.activity_about)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }

            else -> return false
        }
    }

    private fun setupContentView(): View {
        val adsElement = Element().apply {
            title = getResourceString(R.string.responsable)
        }
        val termsElement = Element().apply {
            title = getResourceString(R.string.scriptSlug_terms_description)
        }
        return AboutPage(this)
            .isRTL(false)
            .enableDarkMode(false)
            .setImage(R.mipmap.ic_launcher)
            .setDescription(getString(R.string.app_description, getResourceString(R.string.app_name)))
            .addItem(Element().setTitle(getString(R.string.version, BuildConfig.VERSION_NAME)))
            .addItem(adsElement)
            .addEmail(getResourceString(R.string.email), getResourceString(R.string.contact_us))
            .addItem(termsElement)
            .create()
    }

    companion object {
        fun newIntent(context: Context?): Intent = Intent(context, AboutActivity::class.java)
    }
}