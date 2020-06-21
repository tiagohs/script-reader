package com.tiagohs.script_reader.interactor

import com.tiagohs.script_reader.interactor.base.BaseInteractor
import com.tiagohs.script_reader.interactor.contract.HomeInteractor
import com.tiagohs.script_reader.services.ScriptSlugService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import javax.inject.Inject

class HomeInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(), HomeInteractor {

    override fun fetchMovies(): Observable<InputStream> {
        return scriptSlugService.fetchPDFFromUrl("https://www.scriptslug.com/assets/uploads/scripts/the-good-place-309-janets-2018.pdf")

    }

}