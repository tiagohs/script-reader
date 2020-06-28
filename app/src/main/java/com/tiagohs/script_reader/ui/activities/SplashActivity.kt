package com.tiagohs.script_reader.ui.activities

import android.animation.Animator
import android.os.Bundle
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity: BaseActivity() {

    override val layoutViewId: Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animation_view.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                startActivity(HomeActivity.newIntent(this@SplashActivity))
            }

            override fun onAnimationCancel(p0: Animator?) {
                startActivity(HomeActivity.newIntent(this@SplashActivity))
            }

            override fun onAnimationStart(p0: Animator?) {

            }


        })


    }
}