package com.tiagohs.domain.views

import com.tiagohs.domain.views.base.IView
import com.tiagohs.entities.Script

interface SearchView: IView {

    fun setupContentView()
    fun loadList(list: List<Script>)

    fun showLoading()
    fun hideLoading()
    fun showRecyclerView()
    fun hideRecyclerView()
}