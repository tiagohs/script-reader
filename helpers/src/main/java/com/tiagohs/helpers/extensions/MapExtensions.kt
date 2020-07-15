package com.tiagohs.helpers.extensions

import org.json.JSONObject

fun Map<String, String>?.toJsonObject(): JSONObject {
    if (this == null) {
        return JSONObject()
    }

    return JSONObject(this)
}