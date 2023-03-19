package com.tiagohs.script_reader.ui.activities

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tiagohs.helpers.extensions.getResourceString
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        animation_view.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                startActivity(HomeActivity.newIntent(this@SplashActivity))
                finish()
            }

            override fun onAnimationCancel(animation: Animator) {
                startActivity(HomeActivity.newIntent(this@SplashActivity))
                finish()
            }

            override fun onAnimationRepeat(animation: Animator) {}
        })
    }
}