package com.tiagohs.components.placeholder

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.tiagohs.components.R
import com.tiagohs.components.placeholder.enums.PlaceholderViewType
import kotlinx.android.synthetic.main.layout_placeholder_base.view.*
import java.lang.Exception

class PlaceholderView
@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.layout_placeholder_base, this)

        setupViews(attrs)
    }

    private fun setupViews(attrs: AttributeSet?) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.PlaceholderView)

        try {
            val placeholderViewId = attributes.getResourceId(R.styleable.PlaceholderView_custom_layout, 0)

            if (placeholderViewId != 0) {
                setupPlaceholderView(placeholderViewId)
                return
            }

            setupDefault(attributes)
        } catch(ex: Exception) {
            setupDefault(attributes)
        }

        attributes.recycle()
    }

    private fun setupPlaceholderView(placeholderViewId: Int) {
        loadViewContainer.addView(LayoutInflater.from(this.context).inflate(placeholderViewId, null, false))
    }

    private fun setupDefault(attributes: TypedArray) {
        val placeholderLayout = PlaceholderViewType.getPlaceholderViewLayout(attributes.getInt(R.styleable.PlaceholderView_placeholder_type, -1))

        if (placeholderLayout != 0) {
            setupPlaceholderView(placeholderLayout)
        }
    }

    fun show() {
        placeholderView.showShimmer(true)

        visibility = View.VISIBLE
    }

    fun hide() {
        placeholderView.hideShimmer()
        placeholderView.clearAnimation()

        visibility = View.GONE
    }
}