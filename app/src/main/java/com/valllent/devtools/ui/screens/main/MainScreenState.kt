package com.valllent.devtools.ui.screens.main

data class MainScreenState(
    val proxyIp: String,
    val proxyIpIsCorrect: Boolean,
    val proxiesNames: List<String>,
    val selectedProxyIndex: Int,
    val proxyPermissionsGranted: Boolean,
)