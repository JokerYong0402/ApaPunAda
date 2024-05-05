package com.example.apapunada

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.apapunada.ui.IntroductionPager
import com.example.apapunada.ui.LoginScreen
import com.example.apapunada.ui.components.StaffAppBarPortrait
import com.example.apapunada.ui.staff.StaffDashboardScreen
import com.example.apapunada.ui.users.HomeScreen
import com.example.apapunada.ui.users.MoreScreen
import com.example.apapunada.ui.users.OrderOptionScreen
import com.example.apapunada.ui.users.RewardsScreen
import com.example.apapunada.ui.users.WaitlistScreen
import kotlinx.coroutines.launch

enum class UserScreen(@StringRes val title: Int) {

}

enum class StaffScreen(@StringRes val title: Int, @DrawableRes val icon: Int) {
    Dashboard(R.string.app_name, R.drawable.editprofilepicicon),
    User(R.string.user_mgmt, R.drawable.editprofilepicicon),
    Menu(R.string.menu_mgmt, R.drawable.editprofilepicicon),
    Waitlist(R.string.waitlist_mgmt, R.drawable.editprofilepicicon),
    Ordering(R.string.order_mgmt, R.drawable.editprofilepicicon),
    Feedback(R.string.feedback_mgmt, R.drawable.editprofilepicicon)
}

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

@Composable
fun StaffUI(
    navController: NavHostController = rememberNavController(),
) {
    var currentScreen by remember { mutableStateOf(StaffScreen.Dashboard) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorResource(R.color.primary))
                        .height(100.dp)
                ) {
                    Text(text = stringResource(R.string.app_name))
                }

                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    enumValues<StaffScreen>().forEach { screen ->
                        NavigationDrawerItem(
                            icon = {
                                Icon(
                                    painter = painterResource(screen.icon),
                                    contentDescription = stringResource(screen.title),
                                    modifier = Modifier.size(25.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = screen.name,
                                    color = Color.Black
                                )
                            },
                            selected = true,
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                                navController.navigate(screen.name) {
                                    popUpTo(0)
                                }
                            },
                            colors = NavigationDrawerItemDefaults.colors(
//                                colorResource(R.color.primary)
                                Color.Transparent
                            ),
                            modifier = Modifier.padding(5.dp)
                        )
                        Divider()
                    }
                }
            }
        },

    ) {
        Scaffold(
            topBar = { StaffAppBarPortrait(
                currentScreen = currentScreen,
                drawerState = drawerState,
                coroutineScope = coroutineScope
            )}
        ) { innerPadding ->
            StaffNavigation(
                modifier = Modifier.padding(innerPadding),
                navController = navController,
                currentScreen = { currentScreen = it }
            )
        }
    }
}

@Composable
fun StaffNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    currentScreen: (StaffScreen) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = StaffScreen.Dashboard.name,
        modifier = modifier
    ) {
        composable(route = StaffScreen.Dashboard.name){
            currentScreen(StaffScreen.Dashboard)
            StaffDashboardScreen()
        }

        composable(route = StaffScreen.User.name){

        }

        composable(route = StaffScreen.Menu.name){
            currentScreen(StaffScreen.Menu)
            //MenuScreen()
        }

        composable(route = StaffScreen.Waitlist.name){
            currentScreen(StaffScreen.Waitlist)
            WaitlistScreen(
                onBackButtonClicked = { navController.navigateUp() }
            )
        }

        composable(route = StaffScreen.Ordering.name){
            currentScreen(StaffScreen.Ordering)
            OrderOptionScreen(
                onBackButtonClicked = { navController.navigateUp() }
            )
        }

        composable(route = StaffScreen.Feedback.name){

        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ApaPunAdaAppPreview() {
    StaffUI()
}