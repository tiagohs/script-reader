package com.tiagohs.domain.presenter

import com.tiagohs.components.alert_snackbar.enums.MessageType
import com.tiagohs.domain.R
import com.tiagohs.domain.interactor.contract.CategoryInteractor
import com.tiagohs.domain.presenter.base.BasePresenter
import com.tiagohs.domain.presenter.contract.CategoryPresenter
import com.tiagohs.domain.views.CategoryView
import com.tiagohs.entities.Category
import com.tiagohs.entities.Script
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoryPresenterImpl
@Inject constructor(
    interactor: CategoryInteractor
) : BasePresenter<CategoryView, CategoryInteractor>(interactor),
    CategoryPresenter {

    private var category: Category? = null

    override fun onBindView(view: CategoryView) {
        super.onBindView(view)

        this.view?.let {
            it.setupAlguments()
            it.setTitle(category?.title)
            it.setupContentView()
            it.showLoading()
        }

        fetchScripts()
    }

    override fun setArguments(category: Category) {
        this.category = category
    }

    private fun fetchScripts() {
        val interactor = interactor ?: return
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
        view?.hideLoading()
        view?.loadList(list)
    }

    private fun onFetchHomeContentError(error: Throwable) {
        view?.hideLoading()
        view?.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            val view = view ?: return@showMessage

            onBindView(view)
        }
    }
}