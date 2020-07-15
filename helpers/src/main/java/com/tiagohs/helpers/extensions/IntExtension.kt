package com.tiagohs.helpers.extensions

import android.content.ClipData
import android.content.ContentResolver
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.util.DisplayMetrics
import android.util.TypedValue

fun Int.convertIntToDp(context: Context?): Int {
    val resources = context?.resources ?: return 0
    val intDp = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        resources.displayMetrics
    )

    return intDp.toInt()
}

fun Int.convertIntToPixel(context: Context?): Int {
    val valueDP = this.convertIntToDp(context)
    val metrics = Resources.getSystem().displayMetrics

    return valueDP * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
}

fun Int.toUri(context: Context?): Uri? {
    context ?: return null
    val resources = context.resources ?: return null

    return Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(this))
        .appendPath(resources.getResourceTypeName(this))
        .appendPath(resources.getResourceEntryName(this))
        .build()
}

fun Int.toClipDataUri(label: Int, context: Context?): ClipData? {
    val uri = toUri(context) ?: return null

    return uri.toClipDataUri(label, context)
}