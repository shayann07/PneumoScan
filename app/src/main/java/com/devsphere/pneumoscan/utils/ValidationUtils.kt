package com.devsphere.pneumoscan.utils

object ValidationUtils {
    fun isEmailValid(email: String): Boolean = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun isPasswordStrong(password: String): Boolean {
        val hasLower = password.any { it.isLowerCase() }
        val hasUpper = password.any { it.isUpperCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSymbol = password.any { !it.isLetterOrDigit() }
        return hasLower && hasUpper && hasDigit && hasSymbol && password.length >= 6
    }
}
