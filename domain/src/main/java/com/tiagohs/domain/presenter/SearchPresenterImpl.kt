package com.tiagohs.domain.presenter

import com.tiagohs.domain.interactor.contract.SearchInteractor
import com.tiagohs.domain.presenter.base.BasePresenter
import com.tiagohs.domain.presenter.contract.SearchPresenter
import com.tiagohs.domain.views.SearchView
import com.tiagohs.domain.R
import com.tiagohs.entities.Script
import com.tiagohs.components.alert_snackbar.enums.MessageType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenterImpl
@Inject constructor(
    interactor: com.tiagohs.domain.interactor.contract.SearchInteractor
) : BasePresenter<SearchView, SearchInteractor>(interactor),
    SearchPresenter {

    override fun onBindView(view: SearchView) {
        super.onBindView(view)

        this.view?.setupContentView()
    }

    override fun searchScripts(query: String) {
        if (query.isEmpty()) { return }
        val interactor = interactor ?: return

        view?.showLoading()
        view?.hideRecyclerView()

        subscribers.clear()
        subscribers = CompositeDisposable()

        add(interactor.searchScripts(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onSearchScriptsSuccess(it) },
                { onSearchScriptsError(it) }
            )
        )
    }

    private fun onSearchScriptsSuccess(list: List<Script>) {
        view?.hideLoading()
        view?.showRecyclerView()

        view?.loadList(list)
    }

    private fun onSearchScriptsError(error: Throwable) {
        view?.hideLoading()
        view?.showMessage(error, com.tiagohs.components.alert_snackbar.enums.MessageType.ERROR, R.string.unknown_error) {
            val view = view ?: return@showMessage

            onBindView(view)
        }
    }
}