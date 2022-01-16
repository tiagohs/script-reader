package com.tiagohs.domain.interactor

import com.tiagohs.domain.interactor.base.BaseInteractor
import com.tiagohs.domain.interactor.contract.HomeInteractor
import com.tiagohs.domain.services.ScriptSlugService
import com.tiagohs.entities.home.HomeCell
import com.tiagohs.entities.home.ListDefaultCell
import com.tiagohs.entities.home.ListSpecialCell
import com.tiagohs.helpers.R
import com.tiagohs.helpers.enums.HomeSpecialListEnum
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function4
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class HomeInteractorImpl
@Inject constructor(
    val scriptSlugService: ScriptSlugService
) : BaseInteractor(),
    HomeInteractor {

    override fun fetchHomeContent(): Observable<List<HomeCell>> {
        return Observable.zip(
            scriptSlugService.fetchHomeData().subscribeOn(Schedulers.io()),
            fetchOscarContent().subscribeOn(Schedulers.io()),
            fetchFamilyContent().subscribeOn(Schedulers.io()),
            fetchNetflixContent().subscribeOn(Schedulers.io()),
            Function4 { baseHomeList: List<HomeCell>, oscarHomeContent: HomeCell, familyHomeContent: HomeCell, netflixHomeContent: HomeCell ->
                return@Function4 baseHomeList.toMutableList().apply {
                    add(2, oscarHomeContent)
                    add(familyHomeContent)
                    add(netflixHomeContent)
                }
            }
        )
    }

    private fun fetchOscarContent(): Observable<HomeCell> {
        return scriptSlugService.fetchScriptsByCategory("https://www.scriptslug.com/feature/oscar-nominated-scripts-2021")
            .map { ListSpecialCell(
                title = R.string.home_special_oscar_2021_title,
                subtitle = R.string.home_special_oscar_2021_subtitle,
                config = HomeSpecialListEnum.OSCAR_2020,
                list = it
            ) }
    }

    private fun fetchFamilyContent(): Observable<HomeCell> {
        return scriptSlugService.fetchScriptsByCategory("scripts/category/family")
            .map { ListDefaultCell(
                title = R.string.home_list_family_title,
                list = it
            ) }
    }

    private fun fetchNetflixContent(): Observable<HomeCell> {
        return scriptSlugService.fetchScriptsByCategory("scripts/category/netflix")
            .map { ListDefaultCell(
                title = R.string.home_list_netflix_title,
                list = it
            ) }
    }
}