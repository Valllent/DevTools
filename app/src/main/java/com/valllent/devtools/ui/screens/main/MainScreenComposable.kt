package com.valllent.devtools.ui.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.valllent.devtools.managers.PermissionRequester
import com.valllent.devtools.ui.views.SegmentedButtons

@Composable
fun MainScreen(
    state: MainScreenState,
    actions: MainScreenActions,
) {
    PermissionRequester.rememberPermissionRequester(
        permissions = PermissionRequester.permissions,
        onPermissionGrantedStateChange = { allPermissionsGranted ->
            if (allPermissionsGranted) {
                actions.onPermissionsGranted()
            }
        }
    )

    Column {
        SegmentedButtons(
            state.proxiesNames,
            state.selectedProxyIndex,
            enabled = state.proxyPermissionsGranted,
            modifier = Modifier.padding(16.dp),
            onButtonClick = {
                actions.onProxySelect(it)
            }
        )
    }
}