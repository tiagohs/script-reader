package com.tiagohs.script_reader.helpers.extensions

import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

fun ViewPager.getFragmentAtPosition(position: Int): Fragment? = adapter?.instantiateItem(this, position) as? Fragment