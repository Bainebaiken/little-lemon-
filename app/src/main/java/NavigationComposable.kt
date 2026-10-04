package com.heaven.littlelemon1

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigation(
    navController: NavHostController
) {

    val sharedPreferences =
        navController.context.getSharedPreferences(
            "LittleLemon",
            Context.MODE_PRIVATE
        )

    val firstName =
        sharedPreferences.getString(
            "firstName",
            null
        )

    val startDestination =
        if (firstName == null) {
            Onboarding.route
        } else {
            Home.route
        }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Onboarding.route) {
            Onboarding(navController)
        }

        composable(Home.route) {
            Home(navController)
        }

        composable(Profile.route) {
            Profile(navController)
        }
    }
}