package com.valllent.devtools.ui.screens.main

data class MainScreenActions(
    val onProxySelect: (Int) -> Unit,
    val onPermissionsGranted: () -> Unit,
)
