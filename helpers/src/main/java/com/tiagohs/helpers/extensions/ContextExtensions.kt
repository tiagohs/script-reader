package com.tiagohs.helpers.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.widget.Toast
import com.tiagohs.helpers.Constants
import com.tiagohs.helpers.enums.FontEnum


@Suppress("DEPRECATION")
fun Context?.isNetworkAvailable(): Boolean {
    if (this == null) {
        return false
    }

    val cm = (getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager) ?: return false

    return if (Build.VERSION.SDK_INT < 23) {
        val ni = cm.activeNetworkInfo
        if (ni != null) {
            ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
        }

        false
    } else {
        val n = cm.activeNetwork
        if (n != null) {
            val nc = cm.getNetworkCapabilities(n) ?: return false

            nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        }

        false
    }
}

fun Context?.toast(message: String?, duration: Int = Toast.LENGTH_LONG) {
    if (this == null || message == null) {
        return
    }

    Toast.makeText(this, message, duration).show()
}

/***** SIZES ******/

val Context?.screenHeight: Int
    get() {
        return this?.resources?.displayMetrics?.heightPixels ?: 0
    }

/***** RESOURCES ******/

fun Context.getResourceColor(colorName: String?): Int {
    val colorIdentifier = resources.getIdentifier(colorName, "color", packageName)

    return getResourceColor(colorIdentifier)
}

@Suppress("DEPRECATION")
fun Context.getResourceColor(resource: Int): Int {

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        getColor(resource)
    } else {
        resources.getColor(resource)
    }
}

fun Context?.getResourceDrawable(name: String): Drawable? {
    if (this == null) {
        return null
    }

    val drawableIdentifier = resources.getIdentifier(name, "drawable", packageName)

    return getResourceDrawable(drawableIdentifier)
}

@Suppress("DEPRECATION")
fun Context?.getResourceDrawable(resource: Int): Drawable? {
    if (this == null) {
        return null
    }

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        getDrawable(resource)
    } else {
        resources.getDrawable(resource)
    }
}

fun Context?.getResourceString(resource: Int?): String {
    if (this == null) {
        return ""
    }
    resource ?: return ""

    return resources.getString(resource)
}

fun Context?.getResourceString(name: String?): String {
    if (this == null) {
        return ""
    }
    name ?: return ""

    val stringIdentifier = resources.getIdentifier(name, "string", packageName)

    return getResourceString(stringIdentifier)
}

fun Context?.getResourceArrayString(resource: Int?): List<String> {
    if (this == null) {
        return emptyList()
    }

    resource ?: return emptyList()

    return resources.getStringArray(resource).toList()
}

fun Context.getResourceFont(fontEnum: FontEnum): Typeface {
    return getResourceFont(fontEnum.fontName)
}

fun Context.getResourceFont(fontName: String): Typeface {
    return Typeface.createFromAsset(assets, "fonts/${fontName}${Constants.FONTS.FONT_EXTENSION}")
}

/***** SHARED PREFERENCE ******/

fun Context?.getPreferenceInt(key: String, defaultValue: Int = 0): Int {
    if (this == null) {
        return defaultValue
    }

    return getSharedPreferences(Constants.SHARED_PREFERENCES.DEFAULT, Context.MODE_PRIVATE).getInt(
        key,
        defaultValue
    )
}

@SuppressLint("ApplySharedPref")
fun Context?.setPreference(key: String, value: Int) {
    if (this == null) {
        return
    }

    getSharedPreferences(Constants.SHARED_PREFERENCES.DEFAULT, Context.MODE_PRIVATE)
        .edit()
        .putInt(key, value)
        .commit()
}

fun Context?.getPreferenceBoolean(key: String, defaultValue: Boolean = false): Boolean {
    if (this == null) {
        return defaultValue
    }

    return getSharedPreferences(
        Constants.SHARED_PREFERENCES.DEFAULT,
        Context.MODE_PRIVATE
    ).getBoolean(key, defaultValue)
}

@SuppressLint("ApplySharedPref")
fun Context?.setPreference(key: String, value: Boolean) {
    if (this == null) {
        return
    }

    getSharedPreferences(Constants.SHARED_PREFERENCES.DEFAULT, Context.MODE_PRIVATE)
        .edit()
        .putBoolean(key, value)
        .commit()
}

fun Context?.getPreferenceString(key: String, defaultValue: String = ""): String {
    if (this == null) {
        return defaultValue
    }

    return getSharedPreferences(
        Constants.SHARED_PREFERENCES.DEFAULT,
        Context.MODE_PRIVATE
    ).getString(key, defaultValue) ?: defaultValue
}

@SuppressLint("ApplySharedPref")
fun Context?.setPreference(key: String, value: String?) {
    if (this == null) {
        return
    }

    getSharedPreferences(Constants.SHARED_PREFERENCES.DEFAULT, Context.MODE_PRIVATE)
        .edit()
        .putString(key, value)
        .commit()
}

@SuppressLint("ApplySharedPref")
fun Context?.removePreference(key: String) {
    if (this == null) {
        return
    }

    getSharedPreferences(Constants.SHARED_PREFERENCES.DEFAULT, Context.MODE_PRIVATE)
        .edit()
        .remove(key)
        .commit()
}

/***** INTENT ******/

fun Context.shareContent(text: String, title: String, image: Uri? = null) {
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND

        putExtra(Intent.EXTRA_TEXT, text)

        type = if (image != null) {
            putExtra(Intent.EXTRA_STREAM, image)
            "image/*"
        } else {
            "text/plain"
        }

        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }

    startActivity(Intent.createChooser(sendIntent, title))
}