package com.tiagohs.script_reader.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.jsoup.nodes.Element
import org.w3c.dom.Document

@Parcelize
data class Category(
    var title: String? = null,
    var url: String? = null,
    var icon: Int? = null
) : Parcelable {
    companion object {
        fun from(element: Element): Category = Category().apply {
            title = element.text()
            url = element.attr("href")
        }
    }
}