package com.estivensh4.maasapp.util

import java.util.regex.Pattern

fun String.containsRegex(regex: String): Boolean {
    val patternCompile = Pattern.compile(regex)
    return patternCompile.matcher(this).matches()
}