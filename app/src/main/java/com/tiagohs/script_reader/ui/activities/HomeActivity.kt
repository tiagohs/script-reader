package com.tiagohs.script_reader.ui.activities

import android.os.Bundle
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.presenter.contract.HomePresenter
import com.tiagohs.script_reader.ui.views.HomeView
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_home.*
import java.io.InputStream
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
}
