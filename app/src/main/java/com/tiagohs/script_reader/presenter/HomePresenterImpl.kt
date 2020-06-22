package com.tiagohs.script_reader.presenter

import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.entities.home.HomeCell
import com.tiagohs.script_reader.helpers.enums.MessageType
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.presenter.base.BasePresenter
import com.tiagohs.script_reader.presenter.contract.HomePresenter
import com.tiagohs.script_reader.ui.views.HomeView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenterImpl
@Inject constructor(
    interactor: HomeInteractor
) : BasePresenter<HomeView, HomeInteractor>(interactor), HomePresenter {

    override fun onBindView(view: HomeView) {
        super.onBindView(view)

        this.view?.let {
            it.setupContentView()
            it.showLoading()
        }

        fetchHomeContent()
    }

    private fun fetchHomeContent() {
        val interactor = interactor ?: return

        add(interactor.fetchHomeContent()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { onFetchHomeContentSuccess(it) },
                { onFetchHomeContentError(it) }
            )
        )
    }

    private fun onFetchHomeContentSuccess(list: List<HomeCell>) {
        view?.hideLoading()
        view?.loadHomeContent(list)
    }

    private fun onFetchHomeContentError(error: Throwable) {
        view?.hideLoading()
        view?.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            val view = view ?: return@showMessage

            onBindView(view)
        }
    }
}