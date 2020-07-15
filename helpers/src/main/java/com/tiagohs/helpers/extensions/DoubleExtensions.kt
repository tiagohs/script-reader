package com.tiagohs.helpers.extensions

import java.text.NumberFormat
import java.util.*


fun Double?.withSuffix(format: String): String {
    if (this == null) {
        return ""
    }
    if (this < 1000) return "" + this

    val exp = (Math.log(this) / Math.log(1000.0)).toInt()

    return String.format(format, this / Math.pow(1000.0, exp.toDouble()))
}

fun Double.toCurrencyBR(): String {
    val numberFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    val valueString: String = numberFormat.format(this)

    return valueString
        .replace("\\s+".toRegex(), "")
        .replace("R$", "R$ ")
}
