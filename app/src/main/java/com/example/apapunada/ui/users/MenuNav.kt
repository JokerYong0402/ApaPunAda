package com.example.apapunada.ui.users

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.R

enum class MenuNav(@StringRes val title: Int){
    Menu(R.string.menu),
    FoodList(R.string.food_list),
    FoodDetail(R.string.food_detail),
    OrderMenu(R.string.order_menu)
}

@Composable
fun MenuNav(
    navController: NavHostController = rememberNavController()
){
    var menutype: String = ""
    var menuId: Int = 0
    NavHost(navController = navController, startDestination = MenuNav.Menu.name) {
        composable(MenuNav.Menu.name){
            MenuScreen(
                onMenuTypeClick = {
                    menutype = it
                    navController.navigate("FoodList")
                                 },
                onDish = {
                    menuId = it
                    navController.navigate("FoodDetail")},
            )
        }
        composable(route = "FoodList"){
            FoodListScreen(
                onBackButtonClicked = {navController.navigate(MenuNav.Menu.name)},
                menuType = menutype,
                onDish = {
                    menuId = it
                    navController.navigate("FoodDetail") }

            )

        }
        composable(route = "FoodDetail"){
            FoodDetailScreen(
                onBackButtonClicked = {navController.navigate(MenuNav.FoodList.name)},
                onOrder = { navController.navigate("OrderMenu") },
                currentDishId =  menuId

            )
            }

    }

}

@Preview
@Composable
fun MenuNavPreview() {
    MenuNav(
    )
}
