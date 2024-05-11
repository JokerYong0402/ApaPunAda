package com.example.apapunada.ui.users

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.R

enum class FeedbackNav(@StringRes val title: Int){
    Feedback(R.string.feedback),
    FeedbackSuccess(R.string.feedback_success),
    Home(R.string.home)
}

@Composable
fun FeedbackNav(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = FeedbackNav.Feedback.name) {
        composable(FeedbackNav.Feedback.name){
            FeedbackScreen(
                onSubmit = { navController.navigate(FeedbackNav.FeedbackSuccess.name)}
            )
        }

        composable(FeedbackNav.FeedbackSuccess.name) {
            FeedbackSuccessScreen(
                onClick = { navController.navigate("home")}
            )
        }

        composable(FeedbackNav.Home.name) {
            HomeScreen(navController = navController)
        }
    }
}

@Composable
@Preview
fun FeedbackNavPreview(

){
    FeedbackNav()
}