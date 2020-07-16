package com.tiagohs.helpers.tools

import android.view.View
import androidx.core.view.ViewCompat
import androidx.viewpager2.widget.ViewPager2
import com.tiagohs.helpers.R

class ScriptSpecialPageTransformation: ViewPager2.PageTransformer {

    override fun transformPage(page: View, position: Float) {
        val offsetPx = page.context.resources.getDimensionPixelOffset(R.dimen.offset)

        val viewPager = page.parent.parent as ViewPager2
        val offset = position * -(3 * offsetPx)
        if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
            if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                page.translationX = -offset
            } else {
                page.translationX = offset
            }
        } else {
            page.translationY = offset
        }
    }
}