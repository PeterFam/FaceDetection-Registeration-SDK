package com.peterfam.valifaysdk.util

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern

object Utils {


    fun isMobileNumberValid(phone: String): Boolean {
        return phone.length >= 9
    }

    fun isEmailValid(email: String): Boolean{
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        // Check the password length
        if (password.length < 8) {
            return false
        }

        // Check for at least one lowercase letter
        val hasLowercase = password.any { it.isLowerCase() }
        if (!hasLowercase) {
            return false
        }

        // Check for at least one uppercase letter
        val hasUppercase = password.any { it.isUpperCase() }
        if (!hasUppercase) {
            return false
        }

        // Check for at least one digit
        val hasDigit = password.any { it.isDigit() }
        if (!hasDigit) {
            return false
        }

        // Check for at least one special character
        return password.containsSpecialCharacters()
    }
    private fun CharSequence.containsSpecialCharacters(): Boolean {
        val pattern: Pattern = Pattern.compile("[!@#$%^&*()_=+{}/.<>|\\[\\]~-]")
        val hasSpecialChar: Matcher = pattern.matcher(this)
        return hasSpecialChar.find()
    }
}