package com.poplogic.blipin.utils

fun String.hardcoded(): String = this

fun String.isEmailValid(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return this.matches(emailRegex)
}