package com.example.task.routes

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.task.data.view.SharedViewModel
import com.example.task.screens.details.DetailScreen
import com.example.task.screens.home.HomeScreen

@Composable
fun NavGraph() {
    val navHostController = rememberNavController()
    val sharedViewModel: SharedViewModel = viewModel()


    NavHost(navController = navHostController, startDestination = home){
        composable(home){ HomeScreen(navController = navHostController, sharedViewModel = sharedViewModel)}
        composable(details){ DetailScreen(
            navController = navHostController,
            sharedViewModel = sharedViewModel) }
    }
}

const val home = "HomeScreen"
const val details = "DetailsScreen"