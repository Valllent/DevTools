package com.valllent.devtools.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valllent.devtools.ui.screens.main.MainScreen
import com.valllent.devtools.ui.screens.main.MainScreenActions
import com.valllent.devtools.ui.screens.main.MainScreenViewModel
import com.valllent.devtools.ui.theme.DevToolsTheme

@Composable
fun MainWrapper() {
    DevToolsTheme {
        val navController = rememberNavController()

        NavHost(
            navController,
            startDestination = Screen.Main.staticRoute,
        ) {

            composable(Screen.Main.staticRoute) {
                Screen.Main.Content()
            }

            composable(Screen.Settings.staticRoute) {
                Screen.Settings.Content()
            }

        }
    }
}

sealed class Screen(
    val staticRoute: String
) {

    object Main : Screen("main") {

        @Composable
        fun Content() {
            val viewModel = hiltViewModel<MainScreenViewModel>()

            val state = viewModel.state.collectAsState().value
            val actions = MainScreenActions(
                onProxySelect = {
                    viewModel.onSelectProxyType(it)
                },
                onPermissionsGranted = {
                    viewModel.onPermissionGranted()
                }
            )

            MainScreen(state, actions)
        }

    }

    object Settings : Screen("settings") {

        @Composable
        fun Content() {

        }

    }

}