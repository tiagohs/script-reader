package com.tiagohs.components.alert_snackbar

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.tiagohs.components.R
import com.tiagohs.components.alert_snackbar.enums.MessageType
import com.tiagohs.helpers.extensions.getResourceString
import com.tiagohs.helpers.extensions.setResourceBackgroundColor

class AlertSnackBar (
        parent: ViewGroup,
        content: com.tiagohs.components.alert_snackbar.AlertSnackBarView
) : BaseTransientBottomBar<AlertSnackBar>(parent, content, content) {

    init {
        getView().setBackgroundColor(ContextCompat.getColor(view.context, android.R.color.transparent))
        getView().setPadding(0, 0, 0, 0)
    }

    companion object {

        fun make(
            view: View?,
            type: MessageType = MessageType.ATTENTION,
            description: Int? = null,
            duration: Int = Snackbar.LENGTH_LONG,
            onTryAgainClicked: (() -> Unit)? = null): AlertSnackBar? =
            make(
                view,
                type,
                view?.context.getResourceString(description),
                duration,
                onTryAgainClicked
            )

        fun make(
            view: View?,
            type: MessageType = MessageType.ATTENTION,
            description: String? = null,
            duration: Int = Snackbar.LENGTH_LONG,
            onTryAgainClicked: (() -> Unit)? = null): AlertSnackBar? {

            val parent = view.findSuitableParent() ?: throw IllegalArgumentException(
                    "No suitable parent found from the given view. Please provide a valid view."
            )
            val context = view?.context ?: return null

            try {
                val customView = com.tiagohs.components.alert_snackbar.AlertSnackBarView(
                    context
                ).apply {
                    layoutParams = ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)

                    setResourceBackgroundColor(R.color.transparent)

                    setup(description, type, onTryAgainClicked)
                }

                return AlertSnackBar(
                    parent,
                    customView
                ).setDuration(duration)
            } catch ( e: Exception){
                return null
            }

        }


    }
}

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null

    do {
        if (view is CoordinatorLayout) {
            // We've found a CoordinatorLayout, use it
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                // If we've hit the decor content view, then we didn't find a CoL in the
                // hierarchy, so use it.
                return view
            } else {
                // It's not the content view but we'll use it as our fallback
                fallback = view
            }
        }

        if (view != null) {
            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
    return fallback
}