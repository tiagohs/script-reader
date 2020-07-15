package com.tiagohs.helpers.extensions

import java.util.regex.Pattern

fun String?.isValidLogin(): Boolean = this != null && (this.isValidEmail() || this.isValidCPF())

fun String?.isValidCPF(): Boolean {
    val patternGeneric = Pattern.compile("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")
    val patternNumbers = Pattern.compile("(?=^((?!((([0]{11})|([1]{11})|([2]{11})|([3]{11})|([4]{11})|([5]{11})|([6]{11})|([7]{11})|([8]{11})|([9]{11})))).)*$)([0-9]{11})")

    var cpf = this
    if (cpf != null && patternGeneric.matcher(cpf).matches()) {
        cpf = cpf.replace("-|\\.".toRegex(), "")
        if (patternNumbers.matcher(cpf).matches()) {
            val numbers = IntArray(11)
            for (i in 0..10) numbers[i] = Character.getNumericValue(cpf[i])
            var i: Int
            var sum = 0
            var factor = 100
            i = 0
            while (i < 9) {
                sum += numbers[i] * factor
                factor -= 10
                i++
            }
            var leftover = sum % 11
            leftover = if (leftover == 10) 0 else leftover
            if (leftover == numbers[9]) {
                sum = 0
                factor = 110
                i = 0
                while (i < 10) {
                    sum += numbers[i] * factor
                    factor -= 10
                    i++
                }
                leftover = sum % 11
                leftover = if (leftover == 10) 0 else leftover
                return leftover == numbers[10]
            }
        }
    }
    return false
}

fun String?.isPassword(): Boolean = !this.isNullOrEmpty()

fun String.isValidEmail(): Boolean {
    val patternEmail = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"

    return Pattern.compile(patternEmail).matcher(this).matches()
}