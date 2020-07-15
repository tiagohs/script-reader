package com.tiagohs.helpers.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

fun EditText?.enable() {
    if (this == null) {
        return
    }

    isEnabled = true
    isFocusableInTouchMode = true
}

fun EditText?.disable() {
    if (this == null) {
        return
    }

    isEnabled = false
    isFocusableInTouchMode = false
}

fun EditText?.showKeyboard() {
    if (this == null) {
        return
    }

    val imm =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager ?: return

    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}