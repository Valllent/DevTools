package com.valllent.devtools.ui.screens.main

import android.content.ContentResolver
import android.content.Context
import android.net.wifi.WifiManager
import android.provider.Settings
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.valllent.devtools.data.ProxyType
import com.valllent.devtools.managers.NetworkManager
import com.valllent.devtools.managers.PermissionRequester
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val networkManager: NetworkManager,
) : ViewModel() {

    private val _state = MutableStateFlow(
        MainScreenState(
            proxiesNames = ProxyType.values().map { it.description },
            selectedProxyIndex = -1,
            proxyPermissionsGranted = false
        )
    )
    val state = _state.asStateFlow()

    fun onPermissionGranted() {
        _state.value = state.value.copy(
            proxyPermissionsGranted = true,
            selectedProxyIndex = networkManager.getProxyType()?.ordinal ?: -1
        )
    }

    fun onSelectProxyType(index: Int) {
        val proxyType = ProxyType.values()[index]
        val proxyActivated = networkManager.activateProxy(proxyType)

        if (proxyActivated.not()) return

        networkManager.restartWifi()
        _state.value = state.value.copy(selectedProxyIndex = index)
    }

}