package com.tiagohs.script_reader.ui.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.tiagohs.domain.presenter.contract.HomePresenter
import com.tiagohs.domain.presenter.contract.ReaderPresenter
import com.tiagohs.helpers.Constants
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import com.tiagohs.domain.views.ReaderView
import com.tiagohs.entities.Script
import com.tiagohs.helpers.extensions.hide
import com.tiagohs.helpers.extensions.show
import kotlinx.android.synthetic.main.activity_reader.*
import kotlinx.android.synthetic.main.activity_reader.toolbar
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import java.io.InputStream
import javax.inject.Inject

class ReaderActivity :
    BaseActivity<ReaderPresenter>(),
    ReaderView {

    override val presenter: ReaderPresenter by inject { parametersOf(this) }

    override val layoutViewId: Int = R.layout.activity_reader

    override fun onMenuItemClickListener(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_open_with -> {
            presenter.onOpenWithClicked()
            true
        }
        else -> false
    }

    override fun openReaderWith(url: String) {
        openUrl(url)
    }

    override fun setupArguments() {
        val script = intent.extras?.getParcelable(Constants.ARGUMENTS.SCRIPT) as? Script
            ?: return

        presenter.setArgument(script)
    }

    override fun setTitle(title: String?) {
        toolbar.title = title
    }

    override fun setupContentView() {
        setupToolbar(toolbar)
    }

    override fun showPDF(pdf: InputStream) {
        pdfView.fromStream(pdf)
            .enableSwipe(true) // allows to block changing pages using swipe
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .onLoad { }
            .onError { }
            .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
            .password(null)
            .spacing(0)
            .autoSpacing(false) // add dynamic spacing to fit each page on its own on the screen
            .fitEachPage(false) // fit each page to the view, else smaller pages are scaled relative to largest page.
            .pageSnap(false) // snap pages to screen boundaries
            .pageFling(false) // make a fling change only a single page like ViewPager
            .nightMode(false) // toggle night mode
            .load();
    }

    override fun showLoadingMessage() {
        messageContainer.show()
    }

    override fun hideLoadingMessage() {
        messageContainer.hide()
    }

    companion object {
        fun newIntent(context: Context?, script: Script): Intent = Intent(context, ReaderActivity::class.java).apply {
            putExtra(Constants.ARGUMENTS.SCRIPT, script)
        }
    }
}