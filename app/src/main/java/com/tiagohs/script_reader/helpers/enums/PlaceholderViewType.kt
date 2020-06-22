package com.tiagohs.script_reader.helpers.enums

import com.tiagohs.script_reader.R

enum class PlaceholderViewType(val type: Int, val layout: Int) {
    HOME(0, R.layout.placeholder_home_content),
    SCRIPT_DETAILS(1, R.layout.placeholder_script_details),
    UNKNOWN(-1, 0);

    companion object {

        fun getPlaceholderViewLayout(type: Int?): Int = getPlaceholderViewType(type).layout

        fun getPlaceholderViewType(type: Int?): PlaceholderViewType {
            var contentRating = UNKNOWN

            for (current in values()) {
                if (current.type == type) {
                    contentRating = current
                    break
                }
            }

            return contentRating
        }
    }
}
