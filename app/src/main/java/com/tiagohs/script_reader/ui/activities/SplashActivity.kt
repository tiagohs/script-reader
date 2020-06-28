package com.tiagohs.script_reader.ui.activities

import android.os.Bundle
import com.tiagohs.script_reader.R
import com.tiagohs.script_reader.ui.activities.base.BaseActivity

class SplashActivity: BaseActivity() {

    override val layoutViewId: Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(HomeActivity.newIntent(this))
    }
}