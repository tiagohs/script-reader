package com.tiagohs.script_reader.ui.views

import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.ui.views.base.IView

interface HomeView : IView {
    fun setupContentView()
    fun loadHomeContent(homeContent: List<HomeCell>)

    fun showLoading()
    fun hideLoading()
}