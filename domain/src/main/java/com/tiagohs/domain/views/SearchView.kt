package com.tiagohs.domain.views

import com.tiagohs.domain.views.base.IView
import com.tiagohs.entities.Script

interface SearchView: IView {

    fun setupContentView()
    fun loadList(list: List<Script>, isLastPage: Boolean)
    fun loadListMore(list: List<Script>, isLastPage: Boolean)

    fun showLoading()
    fun hideLoading()
    fun showRecyclerView()
    fun hideRecyclerView()
    fun showEmptyContainer()
    fun hideEmptyContainer()
}