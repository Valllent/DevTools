package com.valllent.devtools.managers

import android.content.SharedPreferences

class StorageManager(
    private val preferences: SharedPreferences,
) {

    companion object {
        const val KEY_PROXY_IP_ADDRESS = "KEY_PROXY_IP_ADDRESS"
    }

    var proxyIpAddress: String
        get() = preferences.getString(KEY_PROXY_IP_ADDRESS, "") ?: ""
        set(value) {
            preferences.edit().putString(KEY_PROXY_IP_ADDRESS, value).apply()
        }

}