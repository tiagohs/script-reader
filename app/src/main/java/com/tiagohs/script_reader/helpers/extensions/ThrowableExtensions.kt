package com.tiagohs.script_reader.helpers.extensions

import java.io.IOException

fun Throwable?.hasConnectionError(): Boolean {
    return this is IOException
}