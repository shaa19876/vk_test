package com.vk.test

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vk.presentation.detail.DetailScreen
import com.vk.presentation.detail.DetailViewModel
import com.vk.presentation.main.MainScreen
import com.vk.presentation.main.MainViewModel
import com.vk.test.ui.theme.TestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val mainViewModel: MainViewModel by viewModels()
  private val detailViewModel:DetailViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      val navController = rememberNavController()
      TestTheme {
        Surface(
          modifier = Modifier.fillMaxSize(),
          color = MaterialTheme.colorScheme.background
        ) {
          NavHost(navController = navController, startDestination = "mainScreen") {
            composable("mainScreen") {
              MainScreen(mainViewModel, navController)
            }
            composable(
              route = "detailScreen?id={id}",
              arguments = listOf(
                navArgument("id") { type = NavType.IntType }
              )
            ) { navBackStackEntry ->
              val id = navBackStackEntry.arguments!!.getInt("id")
              DetailScreen(detailViewModel, id = id)
            }
          }
        }
      }
    }
  }
}