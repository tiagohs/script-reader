package com.tiagohs.script_reader.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.ContentViewCallback
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.helpers.enums.MessageType
import com.tiagohs.script_reader.helpers.extensions.getResourceColor
import com.tiagohs.script_reader.helpers.extensions.getResourceString
import com.tiagohs.script_reader.helpers.extensions.setResourceText
import com.tiagohs.script_reader.helpers.extensions.show
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