package com.tiagohs.entities

import android.os.Parcelable
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
    var scriptType: ScriptType = ScriptType.MOVIE
) : Parcelable {

    companion object {
        fun fromList(element: Element?): Script? {
            if (element == null) {
                return null
            }

            return Script().apply {
                poster = element.select(".script picture img").first()?.attr("src")
                title = element.select(".script .leading-relaxed").first()?.text()?.split(" (")?.firstOrNull()
                writers = element.select(".script .mt-2.leading-relaxed")?.text()?.split(", ")?.map { Category(title = it) }
                genres = element.select(".script__details .script__details__wrap .script__categories")?.text()?.split(", ")?.map { Category(title = it) }
                pageUrl = element.select("a")?.attr("href")

                val episodeName = element.select(" .script .font-semibold")
                if (episodeName.size > 1) {
                    val name = episodeName?.get(2)?.text()

                    if (!episodeName.isNullOrEmpty()) {
                        scriptType = ScriptType.TV_SHOW
                        episode = name
                    } else {
                        year = element.select(".script .leading-relaxed")?.text()?.split(Regex("\\(([0-9)]+)\\)"))?.getOrNull(1)?.replace(Regex("[()\\s]"), "")
                    }
                }
            }
        }

        fun fromDetails(element: Element?): Script? {
            if (element == null) {
                return null
            }

            return Script().apply {
                scriptURL = element.select(".site-main article .border-b a")?.attr("href")
                poster = element.select(".site-main article .border-b img")?.attr("src")

                synopses = element.select(".site-main article .border-brown-200 .mx-auto p")?.first()?.text()
                writers = element.select(".site-main article .border-brown-200 .mx-auto a.whitespace-nowrap")
                                ?.mapNotNull { Category.from(it) }
                genres = element.select(".site-main article .not-prose ul li.inline-block a")
                    ?.mapNotNull { Category.from(it) }

                val type = element.select(".site-main .w-full h1 a .text-gray-400.font-normal.mt-2")
                if (type.size > 1) {
                    scriptType = ScriptType.TV_SHOW

                    title = element.select(".site-main article .w-full h1 a div")?.first()?.text()
                    episode = element.select(".site-main article .w-full h1 a div")?.get(1)?.text()
                    season = element.select(".site-main article .w-full h1 a div")?.get(2)?.text()
                    year = element.select(".site-main article .w-full h1 a div")?.get(3)?.text()?.split(" - ")?.firstOrNull()
                } else {
                    title = element.select(".site-main article .w-full h1 a span")?.first()?.text()
                    year = element.select(".site-main article .w-full h1 a div")?.text()?.split(" - ")?.firstOrNull()
                }
            }

        }
    }
}