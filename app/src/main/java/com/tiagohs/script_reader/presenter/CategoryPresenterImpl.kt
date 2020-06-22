package com.tiagohs.script_reader.presenter

import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.entities.Category
import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.helpers.enums.MessageType
import com.tiagohs.script_reader.interactor.contract.CategoryInteractor
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.presenter.base.BasePresenter
import com.tiagohs.script_reader.presenter.contract.CategoryPresenter
import com.tiagohs.script_reader.presenter.contract.HomePresenter
import com.tiagohs.script_reader.ui.views.CategoryView
import com.tiagohs.script_reader.ui.views.HomeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CategoryPresenterImpl
@Inject constructor(
    interactor: CategoryInteractor
) : BasePresenter<CategoryView, CategoryInteractor>(interactor), CategoryPresenter {

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