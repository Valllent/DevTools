package com.valllent.devtools.managers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

class PermissionRequester(
    private val requestLauncher: ManagedActivityResultLauncher<String, Boolean>,
    val permissionGranted: MutableState<Boolean>,
    val permissions: List<String>
) {

    companion object {

        const val TAG = "PermissionRequester"

        val permissions = listOf(
            Manifest.permission.WRITE_SECURE_SETTINGS,
        )

        @Composable
        fun rememberPermissionRequester(
            permissions: List<String>,
            onPermissionGrantedStateChange: (Boolean) -> Unit,
        ): PermissionRequester {
            val context = LocalContext.current
            val allPermissionGrantedState = remember { mutableStateOf(isAllPermissionsGranted(context)) }
            LaunchedEffect(Unit) {
                Log.d(TAG, "All permissions granted: ${allPermissionGrantedState.value}")
                onPermissionGrantedStateChange(allPermissionGrantedState.value)
            }

            val launcher = rememberLauncherForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    val allPermissionsGranted = isAllPermissionsGranted(context)
                    allPermissionGrantedState.value = allPermissionsGranted
                    onPermissionGrantedStateChange(allPermissionsGranted)
                }
            }

            val permissionRequester = remember {
                mutableStateOf(
                    PermissionRequester(launcher, allPermissionGrantedState, permissions)
                )
            }

            return permissionRequester.value
        }

        private fun isPermissionGranted(permission: String, context: Context): Boolean {
            return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }

        private fun isAllPermissionsGranted(context: Context): Boolean {
            return permissions
                .map {
                    val result = isPermissionGranted(it, context)
                    if (!result) {
                        Log.d(TAG, "Permission $it is not granted!")
                    }
                    result
                }
                .contains(false)
                .not()
        }

    }

    fun requestPermission(context: Context) {
        for (permission in permissions) {
            if (isPermissionGranted(permission, context)) return

            requestLauncher.launch(permission)
        }
    }

}