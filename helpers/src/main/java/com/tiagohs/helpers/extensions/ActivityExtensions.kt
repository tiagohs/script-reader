package com.tiagohs.helpers.extensions

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity


fun Activity?.getScreenWidth(): Int {
    if (this == null) return 0
    val displayMetrics = DisplayMetrics()

    windowManager.defaultDisplay.getMetrics(displayMetrics)
    val screenHeight = displayMetrics.heightPixels

    return displayMetrics.widthPixels
}

fun Activity?.getScreenHeight(): Int {
    if (this == null) return 0

    val displayMetrics = DisplayMetrics()
    windowManager.defaultDisplay.getMetrics(displayMetrics)

    return displayMetrics.heightPixels
}


val Activity?.statusBarHeight: Int
    get() {
        val rectangle = Rect()

        this?.window?.decorView?.getWindowVisibleDisplayFrame(rectangle)

        return rectangle.top
    }


fun Activity.openUrl(url: String?) {
    url ?: return

    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
}

fun FragmentActivity.startFragment(fragmentID: Int, fragment: Fragment) {
    val fm = supportFragmentManager
    val f = fm.findFragmentById(fragmentID)

    if (null == f) {
        fm.beginTransaction()
            .add(fragmentID, fragment)
            .commitAllowingStateLoss()
    } else {
        fm.beginTransaction()
            .replace(fragmentID, fragment)
            .commitAllowingStateLoss()
    }
}

fun Activity.openChromeBrowser(url: String?) {
    url ?: return

    var browserPackage = "com.android.chrome"
    val chromeStatus = hasAppEnabled(browserPackage)

    if (!hasAppInstalled(browserPackage) || !chromeStatus) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://"))
        val resolveInfo = packageManager.resolveActivity(
                browserIntent,
                PackageManager.MATCH_DEFAULT_ONLY)
        browserPackage = resolveInfo?.activityInfo?.packageName ?: return
    }

    val intent = Intent(Intent.ACTION_VIEW).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        data = Uri.parse(url)
        setPackage(browserPackage)
    }

    startActivity(intent)
}

fun Activity.hasAppInstalled(uri: String): Boolean {
    return try {
        val packageManager = this.packageManager
        packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)

        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    } catch (e: NullPointerException) {
        false
    }
}

fun Activity.hasAppEnabled(appPackage: String): Boolean {
    return try {
        val appInfo = this.packageManager.getApplicationInfo(appPackage, 0)

        appInfo.enabled
    } catch (e: PackageManager.NameNotFoundException) {
        false
    } catch (e: NullPointerException) {
        false
    }
}