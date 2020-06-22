package com.tiagohs.script_reader.ui.views

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.ui.views.base.IView
import kotlinx.android.synthetic.main.activity_category.*
import java.io.InputStream

interface CategoryView: IView {
    fun setupContentView()
    fun setupAlguments()

    fun setTitle(title: String?)

    fun loadList(list: List<Script>)

    fun showLoading()
    fun hideLoading()
}