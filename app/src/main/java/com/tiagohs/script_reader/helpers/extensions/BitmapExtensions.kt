package com.tiagohs.script_reader.helpers.extensions

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

fun Bitmap?.convertToBase64(): String? {
    if (this == null) { return null }

    val outputStream = ByteArrayOutputStream()

    compress(Bitmap.CompressFormat.JPEG, 100, outputStream)

    return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
}