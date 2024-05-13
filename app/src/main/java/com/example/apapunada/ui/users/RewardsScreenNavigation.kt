package com.example.apapunada.ui.users

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun RewardsScreenNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = "RewardsScreen") {
        composable("RewardsScreen") {
            RewardsScreen(onBackButtonClicked = { navController.navigateUp() },
                onRedeem = { drawableId, voucherRM -> navController.navigate("VoucherRedeem/$drawableId/$voucherRM") },
                onDetails = { drawableId, voucherRM -> navController.navigate("VoucherDetails/$drawableId/$voucherRM") },
                userPoint = 234)
        }

        composable(
            route = "VoucherRedeem/{drawableId}/{voucherRM}",
            arguments = listOf(
                navArgument("drawableId") { type = NavType.IntType },
                navArgument("voucherRM") { type = NavType.StringType }
            )
            ) {
                backStackEntry ->
            val drawableId: Int = backStackEntry.arguments?.getInt("drawableId") ?: 0
            val voucherRM: String = backStackEntry.arguments?.getString("voucherRM") ?: ""
            VoucherRedeem(
                onBackButtonClicked = {navController.navigateUp()},
                painterResource(drawableId),
                voucherRM = voucherRM,
                userPoints = 234)
        }

        composable(
            route = "VoucherDetails/{drawableId}/{voucherRM}",
            arguments = listOf(
                navArgument("drawableId") { type = NavType.IntType },
                navArgument("voucherRM") { type = NavType.StringType }
            )
        ) {
                backStackEntry ->
            val drawableId: Int = backStackEntry.arguments?.getInt("drawableId") ?: 0
            val voucherRM: String = backStackEntry.arguments?.getString("voucherRM") ?: ""
            VoucherDetails(
                painterResource(drawableId),
                voucherRM = voucherRM,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewRewardsScreenNavigation() {
    RewardsScreenNavigation()
}