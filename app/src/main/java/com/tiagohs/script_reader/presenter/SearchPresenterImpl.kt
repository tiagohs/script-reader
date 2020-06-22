package com.tiagohs.script_reader.presenter

import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.entities.Script
import com.tiagohs.script_reader.helpers.enums.MessageType
import com.tiagohs.script_reader.interactor.contract.SearchInteractor
import com.tiagohs.script_reader.presenter.base.BasePresenter
import com.tiagohs.script_reader.presenter.contract.SearchPresenter
import com.tiagohs.script_reader.ui.views.SearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchPresenterImpl
@Inject constructor(
    interactor: SearchInteractor
) : BasePresenter<SearchView, SearchInteractor>(interactor), SearchPresenter {

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
        view?.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            val view = view ?: return@showMessage

            onBindView(view)
        }
    }
}