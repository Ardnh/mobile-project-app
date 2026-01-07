package com.example.mobileprojectapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.io.File
import java.io.IOException
import java.security.GeneralSecurityException
import androidx.core.content.edit

class SecureStorageManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences

    companion object {
        private const val TAG = "SecureStorageManager"
        private const val PREFS_NAME = "secure_prefs"

        // Authentication keys
        const val KEY_AUTH_TOKEN = "auth_token"
        const val KEY_EXPIRED_TOKEN = "expired_at"
        const val KEY_REFRESH_TOKEN = "refresh_token"
        const val KEY_USER_ID = "user_id"
        const val KEY_USERNAME = "username"
        const val KEY_EMAIL = "email"
        const val KEY_IS_LOGGED_IN = "is_logged_in"
        const val KEY_REMEMBER_ME = "remember_me"

        // App Settings keys
        const val KEY_THEME_MODE = "theme_mode"
        const val KEY_LANGUAGE = "language"
        const val KEY_NOTIFICATION_ENABLED = "notification_enabled"
        const val KEY_FIRST_LAUNCH = "first_launch"
        const val KEY_APP_VERSION = "app_version"
    }

    init {
        sharedPreferences = try {
            createEncryptedPreferences()
        } catch (e: GeneralSecurityException) {
            Log.e(TAG, "Security exception creating EncryptedSharedPreferences", e)
            handleCorruptedPrefs()
            createEncryptedPreferences()
        } catch (e: IOException) {
            Log.e(TAG, "IO exception creating EncryptedSharedPreferences", e)
            handleCorruptedPrefs()
            createEncryptedPreferences()
        }
    }

    /**
     * Create encrypted shared preferences with proper configuration
     */
    private fun createEncryptedPreferences(): SharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            PREFS_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Handle corrupted preferences by deleting and recreating
     */
    private fun handleCorruptedPrefs() {
        try {
            val prefsFile = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefsFile.edit(commit = true) { clear() }

            val prefsPath = context.applicationInfo.dataDir + "/shared_prefs/$PREFS_NAME.xml"
            val file = File(prefsPath)
            if (file.exists()) {
                file.delete()
            }

            Log.i(TAG, "Corrupted preferences cleaned up successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error handling corrupted prefs", e)
        }
    }

    // ========== String Operations ==========

    fun saveString(key: String, value: String) {
        try {
            sharedPreferences.edit { putString(key, value) }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving string for key: $key", e)
        }
    }

    fun getString(key: String, defaultValue: String? = null): String? {
        return try {
            sharedPreferences.getString(key, defaultValue)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting string for key: $key", e)
            defaultValue
        }
    }

    // ========== Int Operations ==========

    fun saveInt(key: String, value: Int) {
        try {
            sharedPreferences.edit { putInt(key, value) }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving int for key: $key", e)
        }
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return try {
            sharedPreferences.getInt(key, defaultValue)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting int for key: $key", e)
            defaultValue
        }
    }

    // ========== Boolean Operations ==========

    fun saveBoolean(key: String, value: Boolean) {
        try {
            sharedPreferences.edit { putBoolean(key, value) }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving boolean for key: $key", e)
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return try {
            sharedPreferences.getBoolean(key, defaultValue)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting boolean for key: $key", e)
            defaultValue
        }
    }

    // ========== Float Operations ==========

    fun saveFloat(key: String, value: Float) {
        try {
            sharedPreferences.edit { putFloat(key, value) }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving float for key: $key", e)
        }
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return try {
            sharedPreferences.getFloat(key, defaultValue)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting float for key: $key", e)
            defaultValue
        }
    }

    // ========== Long Operations ==========

    fun saveLong(key: String, value: Long) {
        try {
            sharedPreferences.edit { putLong(key, value) }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving long for key: $key", e)
        }
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return try {
            sharedPreferences.getLong(key, defaultValue)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting long for key: $key", e)
            defaultValue
        }
    }

    // ========== Set<String> Operations ==========

    fun saveStringSet(key: String, value: Set<String>) {
        try {
            sharedPreferences.edit { putStringSet(key, value) }
        } catch (e: Exception) {
            Log.e(TAG, "Error saving string set for key: $key", e)
        }
    }

    fun getStringSet(key: String, defaultValue: Set<String>? = null): Set<String>? {
        return try {
            sharedPreferences.getStringSet(key, defaultValue)
        } catch (e: Exception) {
            Log.e(TAG, "Error getting string set for key: $key", e)
            defaultValue
        }
    }

    // ========== Utility Operations ==========

    /**
     * Check if a key exists in storage
     */
    fun contains(key: String): Boolean {
        return try {
            sharedPreferences.contains(key)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking if key exists: $key", e)
            false
        }
    }

    /**
     * Remove a specific key from storage
     */
    fun remove(key: String) {
        try {
            sharedPreferences.edit { remove(key) }
        } catch (e: Exception) {
            Log.e(TAG, "Error removing key: $key", e)
        }
    }

    /**
     * Clear all data from storage
     */
    fun clearAll() {
        try {
            sharedPreferences.edit().clear().apply()
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing all data", e)
        }
    }

    /**
     * Get all keys in storage
     */
    fun getAllKeys(): Set<String> {
        return try {
            sharedPreferences.all.keys
        } catch (e: Exception) {
            Log.e(TAG, "Error getting all keys", e)
            emptySet()
        }
    }

    // ========== Authentication Helper Methods ==========

    fun saveAuthToken(token: String) = saveString(KEY_AUTH_TOKEN, token)
    fun saveAuthTokenExpireDate(expireDate: String) = saveString(KEY_EXPIRED_TOKEN, expireDate)
    fun getAuthToken(): String? = getString(KEY_AUTH_TOKEN)
    fun getAuthTokenExpireDate(): String? = getString(KEY_EXPIRED_TOKEN)
    fun clearAuthToken() = remove(KEY_AUTH_TOKEN)

    fun saveRefreshToken(token: String) = saveString(KEY_REFRESH_TOKEN, token)
    fun getRefreshToken(): String? = getString(KEY_REFRESH_TOKEN)

    fun saveUserId(userId: String) = saveString(KEY_USER_ID, userId)
    fun getUserId(): String? = getString(KEY_USER_ID)

    fun saveUsername(username: String) = saveString(KEY_USERNAME, username)
    fun getUsername(): String? = getString(KEY_USERNAME)

    fun saveEmail(email: String) = saveString(KEY_EMAIL, email)
    fun getEmail(): String? = getString(KEY_EMAIL)

    fun setLoggedIn(isLoggedIn: Boolean) = saveBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
    fun isLoggedIn(): Boolean = getBoolean(KEY_IS_LOGGED_IN, false)

    fun setRememberMe(remember: Boolean) = saveBoolean(KEY_REMEMBER_ME, remember)
    fun isRememberMe(): Boolean = getBoolean(KEY_REMEMBER_ME, false)

    /**
     * Clear all authentication data at once
     */
    fun clearAuthData() {
        try {
            sharedPreferences.edit().apply {
                remove(KEY_AUTH_TOKEN)
                remove(KEY_REFRESH_TOKEN)
                remove(KEY_USER_ID)
                remove(KEY_USERNAME)
                remove(KEY_EMAIL)
                remove(KEY_IS_LOGGED_IN)
                apply()
            }
            Log.i(TAG, "Auth data cleared successfully")
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing auth data", e)
        }
    }

    // ========== App Settings Helper Methods ==========

    fun saveThemeMode(mode: String) = saveString(KEY_THEME_MODE, mode)
    fun getThemeMode(): String = getString(KEY_THEME_MODE, "system") ?: "system"

    fun saveLanguage(language: String) = saveString(KEY_LANGUAGE, language)
    fun getLanguage(): String = getString(KEY_LANGUAGE, "en") ?: "en"

    fun setNotificationEnabled(enabled: Boolean) = saveBoolean(KEY_NOTIFICATION_ENABLED, enabled)
    fun isNotificationEnabled(): Boolean = getBoolean(KEY_NOTIFICATION_ENABLED, true)

    fun setFirstLaunch(isFirst: Boolean) = saveBoolean(KEY_FIRST_LAUNCH, isFirst)
    fun isFirstLaunch(): Boolean = getBoolean(KEY_FIRST_LAUNCH, true)

    fun saveAppVersion(version: String) = saveString(KEY_APP_VERSION, version)
    fun getAppVersion(): String? = getString(KEY_APP_VERSION)
}