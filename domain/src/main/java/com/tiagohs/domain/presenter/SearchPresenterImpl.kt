package com.tiagohs.domain.presenter

import com.tiagohs.components.alert_snackbar.enums.MessageType
import com.tiagohs.domain.interactor.contract.SearchInteractor
import com.tiagohs.domain.presenter.base.BasePresenter
import com.tiagohs.domain.presenter.contract.SearchPresenter
import com.tiagohs.domain.views.SearchView
import com.tiagohs.domain.R
import com.tiagohs.entities.Script
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class SearchPresenterImpl(
    override val view: SearchView,
    override val interactor: SearchInteractor
) : BasePresenter<SearchView, SearchInteractor>(view, interactor),
    SearchPresenter {

    override fun start() {
        super.start()

        this.view.setupContentView()
    }

    override fun searchScripts(query: String, currentPage: Int, isFirstPage: Boolean) {
        if (query.isEmpty()) { return }

        if (isFirstPage) {
            view.showLoading()
            view.hideRecyclerView()
        }

        subscribers.clear()
        subscribers = CompositeDisposable()

        add(interactor.searchScripts(query, currentPage)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSearchScriptsSuccess(it, isFirstPage) },
                { onSearchScriptsError(it, isFirstPage) }
            )
        )
    }

    private fun onSearchScriptsSuccess(list: List<Script>, isFirstPage: Boolean) {
        view.hideLoading()
        view.showRecyclerView()

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

    private fun onSearchScriptsError(error: Throwable, isFirstPage: Boolean) {
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