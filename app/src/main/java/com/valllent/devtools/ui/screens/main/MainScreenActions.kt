package com.valllent.devtools.ui.screens.main

data class MainScreenActions(
    val onProxySelect: (Int) -> Unit,
    val onProxyIpChange: (String) -> Unit,
    val onPermissionsGranted: () -> Unit,
)
