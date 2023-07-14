package com.valllent.devtools.ui.screens.main

data class MainScreenState(
    val proxiesNames: List<String>,
    val selectedProxyIndex: Int,
    val proxyPermissionsGranted: Boolean,
)