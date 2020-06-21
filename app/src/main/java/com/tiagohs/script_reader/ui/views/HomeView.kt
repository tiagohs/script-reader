package com.tiagohs.script_reader.ui.views

import com.tiagohs.script_reader.ui.views.base.IView
import java.io.InputStream

interface HomeView: IView {

    fun showPDF(pdf: InputStream)
}