package com.tiagohs.helpers.extensions

import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


fun ImageView?.loadImage(
    imageId: Int,
    width: Int,
    height: Int,
    placeholder: Int? = null,
    errorPlaceholder: Int? = null,
    callback: Callback? = null
) {
    if (width == 0 || height == 0) {
        loadImage(imageId, placeholder, errorPlaceholder, callback)
        return
    }

    val imageView = this ?: return
    val picassoRequest = Picasso.get().load(imageId)

    picassoRequest
        .resize(width, height)

    placeholder?.let { picassoRequest.placeholder(it)  }
    errorPlaceholder?.let { picassoRequest.error(it)  }

    if (callback == null) {
        picassoRequest.into(imageView)
        return
    }

    picassoRequest.into(imageView, callback)
}

fun ImageView?.loadImage(
    url: String?,
    width: Int,
    height: Int,
    placeholder: Int? = null,
    errorPlaceholder: Int? = null,
    callback: Callback? = null
) {
    if (width == 0 || height == 0) {
        loadImage(url, placeholder, errorPlaceholder, callback)
        return
    }

    val imageView = this ?: return
    val imageURL = url ?: return
    val picassoRequest = Picasso.get().load(imageURL)

    picassoRequest
        .resize(width, height)

    placeholder?.let { picassoRequest.placeholder(it)  }
    errorPlaceholder?.let { picassoRequest.error(it)  }

    if (callback == null) {
        picassoRequest.into(imageView)
        return
    }

    picassoRequest.into(imageView, callback)
}


fun ImageView?.loadImage(
    url: String?,
    placeholder: Int? = null,
    errorPlaceholder: Int? = null,
    callback: Callback? = null
) {
    val imageView = this ?: return
    val imageURL = url ?: return
    val picassoRequest = Picasso.get().load(imageURL)

    placeholder?.let { picassoRequest.placeholder(it)  }
    errorPlaceholder?.let { picassoRequest.error(it)  }

    if (callback == null) {
        picassoRequest.into(imageView)
        return
    }

    picassoRequest.into(imageView, callback)
}

fun ImageView?.loadImage(
    imageId: Int,
    placeholder: Int? = null,
    errorPlaceholder: Int? = null,
    callback: Callback? = null
) {
    val imageView = this ?: return
    val picassoRequest = Picasso.get().load(imageId)

    placeholder?.let { picassoRequest.placeholder(it)  }
    errorPlaceholder?.let { picassoRequest.error(it)  }

    if (callback == null) {
        picassoRequest.into(imageView)
        return
    }

    picassoRequest.into(imageView, callback)
}

fun ImageView?.setResourceImageDrawable(imageId: Int) {
    if (this == null) {
        return
    }

    setImageDrawable(context.getResourceDrawable(imageId))
}

fun ImageView?.setResourceImageColor(colorId: Int) {
    if (this == null) {
        return
    }

    val drawable = drawable ?: return
    val color = context.getResourceColor(colorId)

    drawable.setTint(color)
}