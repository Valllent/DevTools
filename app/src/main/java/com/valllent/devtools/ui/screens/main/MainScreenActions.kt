package com.valllent.devtools.ui.screens.main

import android.content.Context

data class MainScreenActions(
    val onProxySelect: (Int) -> Unit,
    val onProxyIpChange: (String) -> Unit,
    val onPermissionsGranted: () -> Unit,
    val openDevSettings: (Context) -> Unit,
)
