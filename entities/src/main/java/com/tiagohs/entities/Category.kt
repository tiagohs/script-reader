package com.tiagohs.entities

import android.os.Parcelable
import com.tiagohs.entities.home.ListSpecialCell
import com.tiagohs.helpers.enums.GenresEnum
import kotlinx.android.parcel.Parcelize
import org.jsoup.nodes.Element

@Parcelize
data class Category(
    var title: String? = null,
    var url: String? = null,
    var icon: Int? = null
) : Parcelable {
    companion object {
        fun from(element: Element): Category = Category()
            .apply {
            title = element.text().trim()
            url = element.attr("href")
        }

        fun from(genresEnum: GenresEnum): Category = Category(
            title = genresEnum.name,
            url = genresEnum.url,
            icon = genresEnum.icon
        )
    }
}