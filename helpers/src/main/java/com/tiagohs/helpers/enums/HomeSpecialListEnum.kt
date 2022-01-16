package com.tiagohs.helpers.enums

import com.tiagohs.helpers.R

enum class HomeSpecialListEnum(
    val url: String,
    val backgroundColor: Int,
    val textColor: Int,
    val image: Int
) {
    OSCAR_2020("feature/oscar-nominated-scripts-2021", R.color.secondaryColor, R.color.md_white_1000, R.drawable.ic_img_background_special_oscar)
}