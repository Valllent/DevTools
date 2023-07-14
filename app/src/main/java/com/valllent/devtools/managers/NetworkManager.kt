package com.valllent.devtools.managers

import android.content.ContentResolver
import android.net.wifi.WifiManager
import android.provider.Settings
import android.util.Log
import com.valllent.devtools.data.ProxyType

class NetworkManager(
    private val contentResolver: ContentResolver,
    private val wifiManager: WifiManager,
) {

    companion object {
        const val TAG = "NetworkManager"
    }

    fun getProxyType(): ProxyType? {
        val currentProxy = getCurrentProxy() ?: return null

        return when {
            currentProxy.endsWith(":0") -> {
                ProxyType.NO_PROXY
            }

            currentProxy.endsWith(":8080") -> {
                ProxyType.MITMPROXY
            }

            currentProxy.endsWith(":8888") -> {
                ProxyType.CHARLES
            }

            else -> {
                null
            }
        }
    }

    fun activateProxy(proxyType: ProxyType): Boolean {
        val proxyUrl = proxyType.proxyUrl
        try {
            if (Settings.Global.putString(contentResolver, Settings.Global.HTTP_PROXY, proxyUrl)) {
                Log.d(TAG, "New proxy url: $proxyUrl")
                return true

            } else {
                Log.e(TAG, "Can't set proxy value.")
            }
        } catch (exception: SecurityException) {
            Log.e(TAG, "Can't install proxy. Did you move the app to the system apps?", exception)
        }
        return false
    }

    fun restartWifi() {
        try {
            wifiManager.setWifiEnabled(false)
            wifiManager.setWifiEnabled(true)
            return
        } catch (throwable: Throwable) {
            Log.e(TAG, "Wifi restarting fail (attempt 1)")
        }

        try {
            Settings.Global.putInt(contentResolver, Settings.Global.WIFI_ON, 0)
            Settings.Global.putInt(contentResolver, Settings.Global.WIFI_ON, 1)
        } catch (throwable: Throwable) {
            Log.e(TAG, "Wifi restarting fail (attempt 2)")
        }
    }

    private fun getCurrentProxy(): String? {
        try {
            val currentProxy = Settings.Global.getString(contentResolver, Settings.Global.HTTP_PROXY)
            Log.d(TAG, currentProxy)
            return currentProxy
        } catch (exception: SecurityException) {
            Log.e(TAG, "Can't get proxy info.", exception)
        }
        return null
    }

}