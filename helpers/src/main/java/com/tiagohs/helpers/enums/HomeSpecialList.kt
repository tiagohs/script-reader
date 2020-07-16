package com.tiagohs.helpers.enums

import com.tiagohs.helpers.R

enum class HomeSpecialList(
    val slug: String,
    val backgroundColor: Int,
    val textColor: Int,
    val image: Int
) {
    OSCAR_2020("2020-oscar-nominated", R.color.primaryColor, R.color.md_white_1000, R.drawable.ic_genre_animation)
}