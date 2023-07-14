package com.valllent.devtools.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.valllent.devtools.R
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
        Box(
            modifier = Modifier
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .padding(16.dp),
                value = state.proxyIp,
                onValueChange = {
                    actions.onProxyIpChange(it)
                },
                label = { Text(text = stringResource(id = R.string.proxy_ip)) },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                keyboardActions = KeyboardActions { },
                isError = state.proxyIpIsCorrect.not()
            )
        }
        SegmentedButtons(
            state.proxiesNames,
            state.selectedProxyIndex,
            enabled = state.proxyPermissionsGranted && state.proxyIpIsCorrect,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            onButtonClick = {
                actions.onProxySelect(it)
            }
        )
    }
}