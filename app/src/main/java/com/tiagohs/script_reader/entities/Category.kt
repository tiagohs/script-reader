package com.tiagohs.script_reader.entities

import org.jsoup.nodes.Element
import org.w3c.dom.Document

data class Category(
    var title: String? = null,
    var url: String? = null
) {
    companion object {
        fun from(element: Element): Category = Category().apply {
            title = element.text()
            url = element.attr("href")
        }
    }
}