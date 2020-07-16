package com.tiagohs.domain.presenter

import com.tiagohs.domain.R
import com.tiagohs.domain.interactor.contract.HomeInteractor
import com.tiagohs.domain.presenter.base.BasePresenter
import com.tiagohs.domain.presenter.contract.HomePresenter
import com.tiagohs.domain.views.HomeView
import com.tiagohs.entities.home.HomeCell
import com.tiagohs.components.alert_snackbar.enums.MessageType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomePresenterImpl
@Inject constructor(
    interactor: HomeInteractor
) : BasePresenter<HomeView, HomeInteractor>(interactor),
    HomePresenter {

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