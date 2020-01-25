package com.treewall.av.pizzaapp.utils

import android.util.Patterns
import com.treewall.av.pizzaapp.R
import java.util.regex.Pattern

interface UserCredentialsValidator {
    fun getNameErrorIfInvalid(name: String): Int?

    fun getEmailErrorIfInvalid(email: String): Int?

    fun getPasswordErrorIfInvalid(password: String): Int?

    fun getConfirmPasswordErrorIfInvalid(password: String, confirmPassword: String): Int?
}

class UserCredentialsValidatorImpl : UserCredentialsValidator {
    private val passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9]).{8,}\$")

    override fun getNameErrorIfInvalid(name: String): Int? {
        return if (name.isEmpty()) {
            R.string.error_name_empty
        } else {
            null
        }
    }

    override fun getEmailErrorIfInvalid(email: String): Int? {
        return if (email.isEmpty()) {
            R.string.error_email_empty
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            R.string.error_email_invalid
        } else {
            null
        }
    }

    override fun getPasswordErrorIfInvalid(password: String): Int? {
        return if (!passwordPattern.matcher(password).matches()) {
            R.string.error_password_invalid
        } else {
            null
        }
    }

    override fun getConfirmPasswordErrorIfInvalid(password: String, confirmPassword: String): Int? {
        return if (confirmPassword.isEmpty()) {
            R.string.error_confirm_password_empty
        } else if (password != confirmPassword) {
            R.string.error_passwords_not_same
        } else {
            null
        }
    }
}