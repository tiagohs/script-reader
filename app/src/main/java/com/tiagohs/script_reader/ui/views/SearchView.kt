package com.tiagohs.script_reader.ui.views

import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.helpers.extensions.show
import com.tiagohs.script_reader.ui.views.base.IView
import kotlinx.android.synthetic.main.activity_search.*
import java.io.InputStream

interface SearchView: IView {

    fun setupContentView()
    fun loadList(list: List<Script>)

    fun showLoading()
    fun hideLoading()
    fun showRecyclerView()
    fun hideRecyclerView()
}