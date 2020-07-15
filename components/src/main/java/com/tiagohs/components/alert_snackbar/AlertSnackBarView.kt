package com.tiagohs.components.alert_snackbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.tiagohs.components.R
import com.tiagohs.components.alert_snackbar.enums.MessageType
import com.tiagohs.helpers.extensions.*
import kotlinx.android.synthetic.main.layout_snackbar_base_alert.view.*

class AlertSnackBarView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    init {
        View.inflate(context, R.layout.layout_snackbar_base_alert, this)
        clipToPadding = false

    }

    fun setup(message: Int?, type: MessageType = MessageType.ATTENTION, onTryAgainClicked: (() -> Unit)? = null) {
        setup(context.getResourceString(message), type, onTryAgainClicked)
    }

    fun setup(message: String?, type: MessageType = MessageType.ATTENTION, onTryAgainClicked: (() -> Unit)? = null) {
        cardView.setCardBackgroundColor(context.getResourceColor(type.color))

        title.setResourceText(type.title)
        description.setResourceText(message)

        onTryAgainClicked?.let {
            tryAgainButton.show()
            tryAgainButton.setOnClickListener { onTryAgainClicked.invoke() }
        }
    }

    override fun animateContentIn(delay: Int, duration: Int) {

    }

    override fun animateContentOut(delay: Int, duration: Int) {
    }
}