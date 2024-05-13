package com.example.apapunada

//import com.example.apapunada.ui.staff.StaffWaitlistScreen
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
import com.example.apapunada.ui.components.StaffAppBarPortrait
import com.example.apapunada.ui.staff.StaffDashboardScreen
import com.example.apapunada.ui.staff.StaffFeedbackScreen
import com.example.apapunada.ui.staff.StaffMenuScreen
import com.example.apapunada.ui.staff.StaffOrderScreen
import com.example.apapunada.ui.staff.StaffUserScreen
import com.example.apapunada.ui.staff.StaffWaitlistScreen
import kotlinx.coroutines.launch

enum class UserScreen(@StringRes val title: Int) {
    Introduction(R.string.introduction),
    Login(R.string.login),
    SignUp(R.string.sign_up),
    Home(R.string.home),
    Waitlist(R.string.waitlist),
    Order(R.string.order),
    Rewards(R.string.rewards),
    More(R.string.more),
}

enum class StaffScreen(@StringRes val title: Int, @DrawableRes val icon: Int) {
    Dashboard(R.string.app_name, R.drawable.editprofilepicicon),
    User(R.string.user_mgmt, R.drawable.editprofilepicicon),
    Menu(R.string.menu_mgmt, R.drawable.editprofilepicicon),
    Waitlist(R.string.waitlist_mgmt, R.drawable.editprofilepicicon),
    Ordering(R.string.order_mgmt, R.drawable.editprofilepicicon),
    Feedback(R.string.feedback_mgmt, R.drawable.editprofilepicicon)
}

//@Composable
//fun ApaPunAdaApp(
//    navController: NavHostController = rememberNavController()
//) {
//    NavHost(
//        navController = navController,
//        startDestination = "introduction"
//    ) {
//        composable(UserScreen.Introduction.name) {
//            IntroductionPager(
//                onLoginButtonClicked = { navController.navigate(UserScreen.Login.name) },
////                onSignUpButtonClicked = navController.navigate("")
//                )
//        }
//
//        composable(UserScreen.Login.name) {
//            LoginScreen(
//                onBackButtonClicked = { navController.navigateUp() },
//                onLoginButtonClicked = { navController.navigate(UserScreen.Home.name)}
//            )
//        }
//
//        navigation(startDestination = UserScreen.Home.name, route = "userLoggedIn") {
//            composable(UserScreen.Home.name) {
//                HomeScreen(navController = navController)
//            }
//
//            composable(UserScreen.Waitlist.name) {
//                WaitlistScreen(
//                    onBackButtonClicked = { navController.navigateUp() }
//                )
//            }
//
////            composable("feedback"){
////                FeedbackNav(navController = navController)
////            }
//
//            composable(UserScreen.Order.name) {
//                OrderOptionScreen(
//                    onBackButtonClicked = { navController.navigateUp() }
//                )
//            }
//
//            composable(UserScreen.Rewards.name) {
//                RewardsScreen(
//                    onBackButtonClicked = { navController.navigateUp() },
//                    onRedeem = {drawableId, voucherRM -> navController.navigate("VoucherRedeem/$drawableId/$voucherRM")},
//                    onDetails = {drawableId, voucherRM -> navController.navigate("VoucherDetails/$drawableId/$voucherRM")},
//                    userPoint = 1
//                )
//            }
//
//            composable(UserScreen.More.name) {
//                MoreScreen(navController = navController)
//            }
//        }
//    }
//}

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
            currentScreen(StaffScreen.User)
            StaffUserScreen()
        }

        composable(route = StaffScreen.Menu.name){
            currentScreen(StaffScreen.Menu)
            StaffMenuScreen()
        }

        composable(route = StaffScreen.Waitlist.name){
            currentScreen(StaffScreen.Waitlist)
            StaffWaitlistScreen()
        }

        composable(route = StaffScreen.Ordering.name){
            currentScreen(StaffScreen.Ordering)
            StaffOrderScreen()
        }

        composable(route = StaffScreen.Feedback.name){
            StaffFeedbackScreen()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ApaPunAdaAppPreview() {
    StaffUI()
}