package com.tiagohs.domain.views

import com.tiagohs.domain.views.base.IView
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