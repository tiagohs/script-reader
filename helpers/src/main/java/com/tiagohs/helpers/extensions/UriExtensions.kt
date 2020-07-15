package com.tiagohs.helpers.extensions

import android.content.ClipData
import android.content.Context
import android.net.Uri

fun Uri.toClipDataUri(label: Int, context: Context?): ClipData =
    ClipData.newRawUri(context?.getString(label), this)