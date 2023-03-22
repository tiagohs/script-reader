package com.tiagohs.entities

import android.os.Parcelable
import com.tiagohs.helpers.enums.PaginationType
import com.tiagohs.helpers.enums.ScriptType
import kotlinx.android.parcel.Parcelize
import org.jsoup.nodes.Element

@Parcelize
data class Script(
    var poster: String? = null,
    var title: String? = null,
    var year: String? = null,
    var synopses: String? = null,
    var episode: String? = null,
    var season: String? = null,
    var writers: List<Category>? = null,
    var genres: List<Category>? = null,
    var scriptURL: String? = null,
    var pageUrl: String? = null,
    var scriptType: ScriptType = ScriptType.MOVIE,
    var paginationType: PaginationType = PaginationType.ITEM,
    var releatedScripts: List<Script> = emptyList()
) : Parcelable {

    companion object {
        fun fromList(element: Element?): Script? {
            if (element == null) {
                return null
            }

            return Script().apply {
                poster = element.select("div img").first()?.attr("src")
                title = element.select("div div h3 span").first()?.text()
                year = element.select("div div p").first()?.text()?.split(" - ")?.getOrNull(0)
                pageUrl = element.select("a")?.attr("href")

                val episodeNameDiv = element.select("div div h3 div")
                if (episodeNameDiv.isNotEmpty()) {
                    scriptType = ScriptType.TV_SHOW
                    episode = element.text()
                } else {
                    scriptType = ScriptType.MOVIE
                }
            }
        }

        fun fromDetails(element: Element?): Script? {
            if (element == null) {
                return null
            }

            return Script().apply {
                val baseElement = element.select(".site-body main article")

                scriptURL = baseElement.select("div div a")?.attr("href")
                poster = baseElement.select("div div a div div img")?.attr("src")

                synopses = baseElement.select("div div div div div div p")?.first()?.text()
                writers =  baseElement.select("div div div div .w-full .leading-relaxed a.whitespace-nowrap")
                                ?.mapNotNull { Category.from(it) }
                genres =   baseElement.select("div div div div .w-full div .not-prose ul li")
                    ?.mapNotNull { Category.from(it) }

                val titleMovie = baseElement.select("div div div div div h1 span")

                if (titleMovie.isNotEmpty()) {
                    scriptType = ScriptType.MOVIE
                    title = titleMovie.first()?.text()

                    year = baseElement.select("div div div div div h1 div").first()?.text()?.split(" - ")?.firstOrNull()
                } else {
                    val type = baseElement.select("div div div div div h1 div")

                    scriptType = ScriptType.TV_SHOW

                    episode = type.getOrNull(1)?.text()
                    season = type.getOrNull(2)?.text()
                    year = type.getOrNull(3)?.text()?.split(" - ")?.firstOrNull()
                }

                releatedScripts = element.select(".site-body main section article")?.mapNotNull { fromList(it) } ?: emptyList()
            }
        }
    }
}