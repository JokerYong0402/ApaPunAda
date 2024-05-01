package com.example.apapunada

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.apapunada.ui.IntroductionPager
import com.example.apapunada.ui.LoginScreen
import com.example.apapunada.ui.users.HomeScreen
import com.example.apapunada.ui.users.MoreScreen
import com.example.apapunada.ui.users.OrderOptionScreen
import com.example.apapunada.ui.users.RewardsScreen
import com.example.apapunada.ui.users.WaitlistScreen

@Composable
fun ApaPunAdaApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "introduction") {
        composable("introduction") {
            IntroductionPager(
                onLoginButtonClicked = { navController.navigate("login") },
//                onSignUpButtonClicked = navController.navigate("")
                )
        }

        composable("login") {
            LoginScreen(
                onBackButtonClicked = { navController.navigateUp() },
                onLoginButtonClicked = { navController.navigate("home")}
            )
        }

        navigation(startDestination = "home", route = "userLoggedIn") {
            composable("home") {
                HomeScreen(navController = navController)
            }

            composable("waitlist") {
                WaitlistScreen(
                    onBackButtonClicked = { navController.navigateUp() }
                )
            }

            composable("order") {
                OrderOptionScreen(
                    onBackButtonClicked = { navController.navigateUp() }
                )
            }

            composable("rewards") {
                RewardsScreen(
                    onBackButtonClicked = { navController.navigateUp() },
                    userPoint = 1
                )
            }

            composable("more") {
                MoreScreen(navController = navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApaPunAdaAppPreview() {
    ApaPunAdaApp()
}