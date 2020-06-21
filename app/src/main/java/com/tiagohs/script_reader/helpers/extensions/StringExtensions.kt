package com.tiagohs.script_reader.helpers.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import android.text.Html
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.text.Normalizer

inline fun String.downloadImage(
    crossinline onLoaded: (bitmap: Bitmap) -> Unit = {},
    crossinline onError: (ex: Throwable?) -> Unit = {}
) {
    Picasso.get()
        .load(this)
        .into(object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {}

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                onError(e)
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                if (bitmap == null) {
                    onError(null)
                    return
                }

                onLoaded(bitmap)
            }
        })
}

inline fun String.toImageUri(
    context: Context?,
    crossinline onLoaded: (bitmap: Uri) -> Unit = {},
    crossinline onError: (ex: Throwable?) -> Unit = {}
) {
    this.downloadImage(
        onLoaded = {
            val path = MediaStore.Images.Media.insertImage(context?.contentResolver, it, "", null)
            val imageUri = Uri.parse(path)

            onLoaded(imageUri)
        },
        onError = { onError(it) }
    )
}

fun String.stripHtml(): String {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
    } else {
        Html.fromHtml(this).toString()
    }
}

fun String?.toBearerTokenFormat(): String =
    if (this.isNullOrEmpty()) {
        ""
    } else String.format("Bearer %s", this)

fun String?.stripAccents(): String {
    if (this == null) {
        return ""
    }

    return Normalizer.normalize(this.toLowerCase(), Normalizer.Form.NFD)
        .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "")
}
