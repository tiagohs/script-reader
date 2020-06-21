package com.tiagohs.script_reader.helpers.enums

import android.content.res.AssetManager
import android.graphics.Typeface

enum class FontEnum(
        val fontName: String
) {
    ;

    val fontWithPath: String
        get() = String.format("fonts/%s.otf", this.fontName)

    fun getTypeface(asset: AssetManager?): Typeface {
        return Typeface.createFromAsset(asset, this.fontWithPath)
    }
}