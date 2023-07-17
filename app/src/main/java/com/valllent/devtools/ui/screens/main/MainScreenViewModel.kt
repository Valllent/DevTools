package com.valllent.devtools.ui.screens.main

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.InetAddresses
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import com.valllent.devtools.data.ProxyType
import com.valllent.devtools.managers.StorageManager
import com.valllent.devtools.managers.SystemSettingsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val systemSettingsManager: SystemSettingsManager,
    private val storageManager: StorageManager,
) : ViewModel() {

    companion object {
        const val TAG = "MainScreenViewModel"
    }

    private val _state: MutableStateFlow<MainScreenState>

    init {
        val proxyIpAddress = storageManager.proxyIpAddress
        _state = MutableStateFlow(
            MainScreenState(
                proxyIp = proxyIpAddress,
                proxyIpIsCorrect = proxyIpAddress.isNotBlank(),
                proxiesNames = ProxyType.values().map { it.description },
                selectedProxyIndex = -1,
                proxyPermissionsGranted = false
            )
        )
    }

    val state = _state.asStateFlow()

    fun onProxyIpChange(newIp: String) {
        val isIpCorrect = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            InetAddresses.isNumericAddress(newIp)
        } else {
            @Suppress("DEPRECATION")
            Patterns.IP_ADDRESS.matcher(newIp).matches()
        }

        if (isIpCorrect) {
            storageManager.proxyIpAddress = newIp
        }

        _state.value = state.value.copy(
            proxyIp = newIp,
            proxyIpIsCorrect = isIpCorrect
        )
    }

    fun onPermissionGranted() {
        _state.value = state.value.copy(
            proxyPermissionsGranted = true,
            selectedProxyIndex = systemSettingsManager.getProxyType()?.ordinal ?: -1
        )
    }

    fun onSelectProxyType(index: Int) {
        val proxyType = ProxyType.values()[index]
        val proxyActivated = systemSettingsManager.activateProxy(proxyType)

        if (proxyActivated.not()) return

        systemSettingsManager.restartWifi()
        _state.value = state.value.copy(selectedProxyIndex = index)
    }

    fun openDevSettings(context: Context) {
        if (context !is Activity) {
            Log.e(TAG, "Can't convert Context to Activity")
            return
        }

        context.startActivityForResult(
            Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS),
            0
        )
    }

}