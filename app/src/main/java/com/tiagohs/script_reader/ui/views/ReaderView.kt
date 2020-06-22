package com.tiagohs.script_reader.ui.views

import com.tiagohs.script_reader.ui.views.base.IView
import java.io.InputStream

interface ReaderView : IView {

    fun setupArguments()
    fun setupContentView()
    fun setTitle(title: String?)

    fun showPDF(pdf: InputStream)
    fun openReaderWith(url: String)

    fun showLoadingMessage()
    fun hideLoadingMessage()
}