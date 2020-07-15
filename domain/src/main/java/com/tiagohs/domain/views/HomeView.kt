package com.tiagohs.domain.views

import com.tiagohs.domain.views.base.IView
import com.tiagohs.entities.home.HomeCell

interface HomeView : IView {
    fun setupContentView()
    fun loadHomeContent(homeContent: List<HomeCell>)

    fun showLoading()
    fun hideLoading()
}