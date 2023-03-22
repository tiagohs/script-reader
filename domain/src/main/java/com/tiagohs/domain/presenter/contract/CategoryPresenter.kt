package com.tiagohs.domain.presenter.contract

import com.tiagohs.domain.presenter.base.IPresenter
import com.tiagohs.domain.views.CategoryView
import com.tiagohs.entities.Category

interface CategoryPresenter :
    IPresenter {
    fun fetchScripts(currentPage: Int, isFirstPage: Boolean)
    fun setArguments(category: Category)
}