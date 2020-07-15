package com.tiagohs.helpers.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.*
import androidx.core.content.ContextCompat

/***** VISIBILITIES ******/

fun View?.show() {
    if (this == null) {
        return
    }

    this.visibility = View.VISIBLE
}

fun View?.hide() {
    if (this == null) {
        return
    }

    this.visibility = View.GONE
}

fun View?.invisible() {
    if (this == null) {
        return
    }

    this.visibility = View.INVISIBLE
}

fun Array<View>.show() = this.map { it.show() }
fun Array<View>.hide() = this.map { it.hide() }

/***** RESOURCES ******/

fun View?.setResourceBackground(resId: Int) {
    if (this == null) {
        return
    }

    background = context.getResourceDrawable(resId)
}

fun View?.setResourceBackgroundColor(colorRes: Int) {
    if (this == null) {
        return
    }

    setBackgroundColor(context.getResourceColor(colorRes))
}

fun View?.setResourceBackgroundTint(colorRes: Int) {
    if (this == null) {
        return
    }

    backgroundTintList = ContextCompat.getColorStateList(context, colorRes)
}

/***** ANIMATIONS ******/

fun View?.fade(
    startAlpha: Float,
    endAlpha: Float,
    duration: Long = 300L
) {
    if (this == null) {
        return
    }

    alpha = startAlpha

    animate()
        .alpha(endAlpha)
        .duration = duration
}

fun View?.fadeIn() {
    fade(0f, 1f)
}

fun View?.fadeOut() {
    fade(1f, 0f)
}

fun View?.slideWithFade() {
    if (this == null) {
        return
    }

    AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@slideWithFade, "alpha", 0f, 1f),
            ObjectAnimator.ofFloat(
                this@slideWithFade,
                "translationX",
                this@slideWithFade.width / 4.toFloat(),
                0f
            )
        )
        interpolator = AccelerateDecelerateInterpolator()
        duration = 500

        start()
    }
}

fun View?.pulse() {
    if (this == null) {
        return
    }

    AnimatorSet().apply {
        playTogether(
            ObjectAnimator.ofFloat(this@pulse, "scaleY", 1f, 1.2f, 1f, 1.2f, 1f),
            ObjectAnimator.ofFloat(this@pulse, "scaleX", 1f, 1.2f, 1f, 1.2f, 1f)
        )
        interpolator = AccelerateDecelerateInterpolator()
        duration = 500
        start()
    }
}

fun View?.crossFade(viewOut: View? = null) {
    if (this == null) {
        return
    }

    viewOut?.let {
        AlphaAnimation(1.0f, 0.5f).apply {
            duration = 200

            this@crossFade.invisible()

            it.startAnimation(this)
        }
    }

    AlphaAnimation(0.2f, 1.0f).apply {
        startOffset = 100
        duration = 200
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                viewOut.invisible()
            }

            override fun onAnimationEnd(animation: Animation) {}
            override fun onAnimationRepeat(animation: Animation) {}
        })

        this@crossFade.show()

        alpha = 1f
        startAnimation(this)
    }

}

fun View?.scale() {
    if (this == null) {
        return
    }



    ScaleAnimation(
        0.5f,
        1f,
        0.5f,
        1f,
        Animation.RELATIVE_TO_SELF,
        0.5f,
        Animation.RELATIVE_TO_SELF,
        0.5f
    ).apply {
        this@scale.show()

        duration = 350
        interpolator = OvershootInterpolator(1.5f)

        this@scale.startAnimation(this)
    }

}
