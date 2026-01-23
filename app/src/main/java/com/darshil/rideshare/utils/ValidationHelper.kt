package com.darshil.rideshare.utils

import android.util.Patterns

object ValidationHelper {

    // Email validation
    fun validateEmail(email: String): ValidationResult {
        return when {
            email.isEmpty() -> ValidationResult.Error("Email cannot be empty")
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                ValidationResult.Error("Invalid email format")
            else -> ValidationResult.Success
        }
    }

    // Password validation
    fun validatePassword(password: String): ValidationResult {
        return when {
            password.isEmpty() -> ValidationResult.Error("Password cannot be empty")
            password.length < 6 ->
                ValidationResult.Error("Password must be at least 6 characters")
            else -> ValidationResult.Success
        }
    }

    // Strong password validation
    fun validateStrongPassword(password: String): ValidationResult {
        if (password.isEmpty()) {
            return ValidationResult.Error("Password cannot be empty")
        }

        if (password.length < 8) {
            return ValidationResult.Error("Password must be at least 8 characters")
        }

        if (!password.matches(Regex(".*[A-Z].*"))) {
            return ValidationResult.Error("Must contain uppercase letter")
        }

        if (!password.matches(Regex(".*[a-z].*"))) {
            return ValidationResult.Error("Must contain lowercase letter")
        }

        if (!password.matches(Regex(".*\\d.*"))) {
            return ValidationResult.Error("Must contain a number")
        }

        return ValidationResult.Success
    }

    // Phone validation
    fun validatePhone(phone: String): ValidationResult {
        val cleanPhone = phone.replace(Regex("[\\s()-]"), "")

        return when {
            phone.isEmpty() -> ValidationResult.Error("Phone number cannot be empty")
            cleanPhone.length < 10 ->
                ValidationResult.Error("Phone number must be at least 10 digits")
            !cleanPhone.matches(Regex("^\\d{10,15}$")) ->
                ValidationResult.Error("Invalid phone number")
            else -> ValidationResult.Success
        }
    }

    // Name validation
    fun validateName(name: String): ValidationResult {
        return when {
            name.isEmpty() -> ValidationResult.Error("Name cannot be empty")
            name.length < 2 -> ValidationResult.Error("Name must be at least 2 characters")
            !name.matches(Regex("^[a-zA-Z\\s]{2,50}$")) ->
                ValidationResult.Error("Name can only contain letters and spaces")
            else -> ValidationResult.Success
        }
    }

    // Confirm password validation
    fun validatePasswordMatch(password: String, confirmPassword: String): ValidationResult {
        return when {
            confirmPassword.isEmpty() ->
                ValidationResult.Error("Please confirm your password")
            password != confirmPassword ->
                ValidationResult.Error("Passwords don't match")
            else -> ValidationResult.Success
        }
    }

    // Generic not empty validation
    fun validateNotEmpty(value: String, fieldName: String): ValidationResult {
        return if (value.isEmpty()) {
            ValidationResult.Error("$fieldName cannot be empty")
        } else {
            ValidationResult.Success
        }
    }
}

// Validation result sealed class
sealed class ValidationResult {
    object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}