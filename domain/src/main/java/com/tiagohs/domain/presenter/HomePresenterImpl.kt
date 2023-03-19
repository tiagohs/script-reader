package com.tiagohs.domain.presenter

import com.tiagohs.domain.R
import com.tiagohs.domain.interactor.contract.HomeInteractor
import com.tiagohs.domain.presenter.base.BasePresenter
import com.tiagohs.domain.presenter.contract.HomePresenter
import com.tiagohs.domain.views.HomeView
import com.tiagohs.entities.home.HomeCell
import com.tiagohs.components.alert_snackbar.enums.MessageType
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class HomePresenterImpl(
    override val view: HomeView,
    override val interactor: HomeInteractor
) : BasePresenter<HomeView, HomeInteractor>(view, interactor),
    HomePresenter {

    override fun start() {
        super.start()

        this.view.apply {
            setupContentView()
            showLoading()
        }

        fetchHomeContent()
    }

    private fun fetchHomeContent() {
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
        view.hideLoading()
        view.loadHomeContent(list)
    }

    private fun onFetchHomeContentError(error: Throwable) {
        view.hideLoading()
        view.showMessage(error, MessageType.ERROR, R.string.unknown_error) {
            start()
        }
    }
}