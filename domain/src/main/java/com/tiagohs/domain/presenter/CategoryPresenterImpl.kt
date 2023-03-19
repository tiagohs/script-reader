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
            showLoading()
        }

        fetchScripts()
    }

    override fun setArguments(category: Category) {
        this.category = category
    }

    private fun fetchScripts() {
        val categoryURL = category?.url ?: return

        add(interactor.fetchScriptsByCategory(categoryURL)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onFetchHomeContentSuccess(it) },
                { onFetchHomeContentError(it) }
            )
        )
    }

    private fun onFetchHomeContentSuccess(list: List<Script>) {
        view.hideLoading()
        view.loadList(list)
    }

    private fun onFetchHomeContentError(error: Throwable) {
        view.hideLoading()
        view.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            start()
        }
    }
}