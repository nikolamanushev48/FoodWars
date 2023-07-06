package com.mentormate.foodwars

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ComposeNavigation(route: String) {
    NavHost(navController = rememberNavController(), startDestination = "login_screen") {
        composable(route = route){

        }
    }
}