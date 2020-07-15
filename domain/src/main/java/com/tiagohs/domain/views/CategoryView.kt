package com.tiagohs.domain.views

import com.tiagohs.domain.views.base.IView
import com.tiagohs.entities.Script

interface CategoryView: IView {
    fun setupContentView()
    fun setupAlguments()

    fun setTitle(title: String?)

    fun loadList(list: List<Script>)

    fun showLoading()
    fun hideLoading()
}