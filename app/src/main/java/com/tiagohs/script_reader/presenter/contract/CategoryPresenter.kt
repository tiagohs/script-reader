package com.tiagohs.script_reader.presenter.contract

import com.tiagohs.script_reader.entities.Category
import com.tiagohs.script_reader.presenter.base.IPresenter
import com.tiagohs.script_reader.ui.views.CategoryView

interface CategoryPresenter : IPresenter<CategoryView> {
    fun setArguments(category: Category)
}