package com.tiagohs.helpers.extensions

import android.util.TypedValue
import android.widget.TextView
import com.tiagohs.helpers.enums.FontEnum

fun TextView.setResourceFont(fontName: String?) {
    fontName ?: return
    val context = context ?: return

    typeface = context.getResourceFont(fontName)
}

fun TextView.setResourceFont(fontEnum: FontEnum?) {
    fontEnum ?: return

    setResourceFont(fontEnum.fontName)
}

fun TextView?.setResourceFontSize(fontSize: Int?) {
    if (this == null) {
        return
    }

    setTextSize(
        TypedValue.COMPLEX_UNIT_PX,
        context.resources.getDimension(fontSize ?: 16.convertIntToDp(context))
    )
}

fun TextView?.setResourceText(id: Int) {
    if (this == null) {
        return
    }

    text = context.getResourceString(id)
}

fun TextView?.setResourceText(text: CharSequence?) {
    if (this == null) {
        return
    }

    this.text = text ?: return
}

fun TextView?.setResourceTextColor(color: Int) {
    if (this == null) {
        return
    }

    setTextColor(context.getResourceColor(color))
}

fun TextView?.setResourceTextSize(resId: Int) {
    if (this == null) {
        return
    }

    setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(resId))
}