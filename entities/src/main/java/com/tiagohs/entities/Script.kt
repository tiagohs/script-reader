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
                poster = element.select(".script__poster .script__poster__wrap img").first()?.attr("src")
                title = element.select(".script__details .script__details__wrap .script__title")?.text()?.split(" (")?.firstOrNull()
                year = element.select(".script__details .script__details__wrap .script__title .script__year")?.text()?.replace(Regex("[()\\s]"), "")
                writers = element.select(".script__details .script__details__wrap .script__writers")?.text()?.split(", ")?.map { Category(title = it) }
                genres = element.select(".script__details .script__details__wrap .script__categories")?.text()?.split(", ")?.map { Category(title = it) }
                pageUrl = element.select("a")?.attr("href")

                val episodeName = element.select(" .script__details .script__details__wrap .script__episode-title")?.text()
                if (!episodeName.isNullOrEmpty()) {
                    scriptType = ScriptType.TV_SHOW
                    episode = episodeName
                }

            }
        }

        fun fromDetails(element: Element?): Script? {
            if (element == null) {
                return null
            }

            return Script().apply {
                scriptURL = element.select(".script-single__wrap .script-single__poster a")?.attr("href")
                poster = element.select(".script-single__wrap .script-single__poster a img")?.attr("src")

                title = element.select(".script-single__wrap .script-single__details .script-single__details__wrap .script-single__title")?.first()?.text()?.split(" (")?.firstOrNull()
                year = element.select(".script-single__wrap .script-single__details .script-single__details__wrap .script-single__year")?.first()?.text()?.replace(Regex("[()\\s]"), "")
                synopses = element.select(".script-single__wrap .script-single__details .script-single__details__wrap p")?.first()?.text()

                val episodeName = element.select(".script-single__wrap .script-single__details .script-single__episode-title .script-single__season-episode")?.text()
                if (!episodeName.isNullOrEmpty()) {
                    scriptType = ScriptType.TV_SHOW
                    episode = episodeName
                }

                writers = element.select(".script-single__wrap .script-single__details .script-single__writers a")
                                ?.mapNotNull { Category.from(it) }
                genres = element.select(".script-single__wrap .script-single__details .script-single__categories a")
                    ?.mapNotNull { Category.from(it) }
            }


        }
    }
}