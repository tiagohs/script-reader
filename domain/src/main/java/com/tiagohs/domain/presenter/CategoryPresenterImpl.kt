package com.tiagohs.domain.presenter

import com.tiagohs.components.alert_snackbar.enums.MessageType
import com.tiagohs.domain.R
import com.tiagohs.domain.interactor.contract.CategoryInteractor
import com.tiagohs.domain.presenter.base.BasePresenter
import com.tiagohs.domain.presenter.contract.CategoryPresenter
import com.tiagohs.domain.views.CategoryView
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class CategoryPresenterImpl(
    override val view: CategoryView,
    override val interactor: CategoryInteractor
) : BasePresenter<CategoryView, CategoryInteractor>(view, interactor),
    CategoryPresenter {

    private var category: Category? = null

    override fun start() {
        super.start()

        this.view.apply {
            setupAlguments()
            setTitle(category?.title)
            setupContentView()
        }
    }

    override fun setArguments(category: Category) {
        this.category = category
    }

    override fun fetchScripts(currentPage: Int, isFirstPage: Boolean) {
        val categoryURL = category?.url ?: return

        if (isFirstPage) {
            view.showLoading()
        }

        add(interactor.fetchScriptsByCategory(categoryURL, currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onFetchHomeContentSuccess(it, isFirstPage) },
                { onFetchHomeContentError(it, isFirstPage) }
            )
        )
    }

    private fun onFetchHomeContentSuccess(list: List<Script>, isFirstPage: Boolean) {
        view.hideLoading()

        if (list.isEmpty() && isFirstPage) {
            view.showEmptyContainer()
        } else {
            view.hideEmptyContainer()
        }

        if (isFirstPage) {
            view.loadList(list, isLastPage = list.isEmpty())
            return
        }

        view.loadListMore(list, isLastPage = list.isEmpty())
    }

    private fun onFetchHomeContentError(error: Throwable, isFirstPage: Boolean) {
        if (!isFirstPage) {
            view.loadListMore(emptyList(), isLastPage = true)
        }

        if (isFirstPage) {
            view.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
                start()
            }
        }
    }
}