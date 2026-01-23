package com.darshil.rideshare.utils

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {

    companion object {
        private const val PREF_NAME = "RideSharePrefs"

        // Keys
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_PHONE = "user_phone"
        private const val KEY_HAS_SEEN_ONBOARDING = "has_seen_onboarding"
        private const val KEY_REMEMBER_ME = "remember_me"
        private const val KEY_THEME_MODE = "theme_mode"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    // Login state
    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    // User ID
    var userId: String?
        get() = sharedPreferences.getString(KEY_USER_ID, null)
        set(value) = sharedPreferences.edit().putString(KEY_USER_ID, value).apply()

    // User name
    var userName: String?
        get() = sharedPreferences.getString(KEY_USER_NAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USER_NAME, value).apply()

    // User email
    var userEmail: String?
        get() = sharedPreferences.getString(KEY_USER_EMAIL, null)
        set(value) = sharedPreferences.edit().putString(KEY_USER_EMAIL, value).apply()

    // User phone
    var userPhone: String?
        get() = sharedPreferences.getString(KEY_USER_PHONE, null)
        set(value) = sharedPreferences.edit().putString(KEY_USER_PHONE, value).apply()

    // Onboarding
    var hasSeenOnboarding: Boolean
        get() = sharedPreferences.getBoolean(KEY_HAS_SEEN_ONBOARDING, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_HAS_SEEN_ONBOARDING, value).apply()

    // Remember me
    var rememberMe: Boolean
        get() = sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_REMEMBER_ME, value).apply()

    // Theme mode
    var themeMode: String
        get() = sharedPreferences.getString(KEY_THEME_MODE, "light") ?: "light"
        set(value) = sharedPreferences.edit().putString(KEY_THEME_MODE, value).apply()

    // Save user session
    fun saveUserSession(
        userId: String,
        name: String,
        email: String,
        phone: String
    ) {
        sharedPreferences.edit().apply {
            putBoolean(KEY_IS_LOGGED_IN, true)
            putString(KEY_USER_ID, userId)
            putString(KEY_USER_NAME, name)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_PHONE, phone)
            apply()
        }
    }

    // Clear user session (logout)
    fun clearUserSession() {
        sharedPreferences.edit().apply {
            remove(KEY_IS_LOGGED_IN)
            remove(KEY_USER_ID)
            remove(KEY_USER_NAME)
            remove(KEY_USER_EMAIL)
            remove(KEY_USER_PHONE)
            apply()
        }
    }

    // Clear all preferences
    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}