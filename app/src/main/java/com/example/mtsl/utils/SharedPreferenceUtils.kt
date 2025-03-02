package com.example.mtsl.utils


import android.content.Context
import android.content.SharedPreferences

object SharedPreferenceUtils {

    private const val PREF_NAME = "movies_app_prefs"
    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    private inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        edit().apply { action() }.apply()
    }

    var authToken: String?
        get() = prefs.getString("auth_token", null)
        set(value) = prefs.edit { putString("auth_token", value) }

    var userId: String?
        get() = prefs.getString("user_id", null)
        set(value) = prefs.edit { putString("user_id", value) }

    var darkModeEnabled: Boolean
        get() = prefs.getBoolean("dark_mode", false)
        set(value) = prefs.edit { putBoolean("dark_mode", value) }

    var language: String?
        get() = prefs.getString("app_language", "en")
        set(value) = prefs.edit { putString("app_language", value) }

    var lastWatchedMovieId: String?
        get() = prefs.getString("last_watched_movie", null)
        set(value) = prefs.edit { putString("last_watched_movie", value) }

    fun addToWatchlist(movieId: String) {
        val watchlist = getWatchlist().toMutableSet()
        watchlist.add(movieId)
        prefs.edit { putStringSet("watchlist", watchlist) }
    }

    fun removeFromWatchlist(movieId: String) {
        val watchlist = getWatchlist().toMutableSet()
        watchlist.remove(movieId)
        prefs.edit { putStringSet("watchlist", watchlist) }
    }

    fun getWatchlist(): Set<String> {
        return prefs.getStringSet("watchlist", emptySet()) ?: emptySet()
    }

    fun logout() {
        prefs.edit {
            remove("auth_token")
            remove("user_id")
            remove("watchlist")
        }
    }

    fun isLoggedIn(): Boolean = !authToken.isNullOrEmpty()
}
