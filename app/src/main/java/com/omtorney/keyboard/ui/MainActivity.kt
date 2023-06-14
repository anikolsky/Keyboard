package com.omtorney.keyboard.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omtorney.keyboard.settings.SettingsStorage
import com.omtorney.keyboard.ui.screens.OnboardScreen
import com.omtorney.keyboard.ui.screens.SettingsScreen
import com.omtorney.keyboard.ui.screens.TestScreen
import com.omtorney.keyboard.ui.theme.KeyboardTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeyboardTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    val viewModel: MainViewModel by viewModels {
                        object : ViewModelProvider.Factory {
                            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                val settingsStorage = SettingsStorage(applicationContext)
                                return MainViewModel(settingsStorage) as T
                            }
                        }
                    }
                    val color = viewModel.color.collectAsStateWithLifecycle().value

                    NavHost(
                        navController = navController,
                        startDestination = Screens.Onboard.route
                    ) {
                        composable(route = Screens.Onboard.route) {
                            OnboardScreen(
                                onTestClick = { navController.navigate(Screens.Test.route) },
                                onSettingsClick = { navController.navigate(Screens.Settings.route) }
                            )
                        }
                        composable(route = Screens.Test.route) {
                            TestScreen()
                        }
                        composable(route = Screens.Settings.route) {
                            SettingsScreen(
                                color = Color(color),
                                onEvent = viewModel::onEvent
                            )
                        }
                    }
                }
            }
        }
    }
}

sealed class Screens(val route: String) {
    object Onboard : Screens("onboarding")
    object Test : Screens("testing")
    object Settings : Screens("settings")
}
