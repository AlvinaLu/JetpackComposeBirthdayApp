package com.android.example.jetpackcompose.birthdayapp

import android.app.AlarmManager
import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme

sealed class Routes(val route: String) {
    object Main : Routes("main")
    object Setting : Routes("setting")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Routes.Main.route) {
                    composable(Routes.Main.route) {
                        Surface(color = MaterialTheme.colorScheme.primary, modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary)) {
                            val owner = LocalViewModelStoreOwner.current
                            owner?.let {
                                val viewModel: PersonsViewModel = viewModel(
                                    it,
                                    "MainViewModel",
                                    MainViewModelFactory(
                                        LocalContext.current.applicationContext
                                                as Application
                                    )
                                )

                                MainScreen( viewModel,
                                onIconInfoClicked = {
                                    navController.navigate(Routes.Setting.route)
                                }
                                )
                            }

                        }

                    }
                    composable(Routes.Setting.route) {
                        SecondScreen(
                            onBackPressed = {
                                navController.navigateUp()
                            }
                        )
                    }

                }
            }
        }
    }


}



