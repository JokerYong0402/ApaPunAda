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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.ui.IntroductionPager
import com.example.apapunada.ui.LoginScreen
import com.example.apapunada.ui.components.StaffAppBar
import com.example.apapunada.ui.components.getEnumList
import com.example.apapunada.ui.staff.StaffDashboardScreen
import com.example.apapunada.ui.staff.StaffFeedbackScreen
import com.example.apapunada.ui.staff.StaffMenuScreen
import com.example.apapunada.ui.staff.StaffOrderScreen
import com.example.apapunada.ui.staff.StaffUserScreen
import com.example.apapunada.ui.staff.StaffWaitlistScreen
import com.example.apapunada.ui.users.HomeScreen
import com.example.apapunada.ui.users.MoreScreen
import com.example.apapunada.ui.users.OrderNav
import com.example.apapunada.ui.users.RewardsScreenNavigation
import com.example.apapunada.ui.users.SignUpScreen
import com.example.apapunada.ui.users.WaitlistScreen
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.UserRole
import kotlinx.coroutines.launch

enum class StartScreen(@StringRes val title: Int) {
    Introduction(R.string.introduction),
    Login(R.string.login),
    SignUp(R.string.sign_up),
    Identify(R.string.identify),
    User(R.string.user),
    Staff(R.string.staff),
}

enum class UserScreen(@StringRes val title: Int) {
    Home(R.string.home),
    Waitlist(R.string.waitlist),
    Order(R.string.order),
    Rewards(R.string.rewards),
    More(R.string.more),
}

enum class StaffScreen(@StringRes val title: Int, @DrawableRes val icon: Int) {
    Dashboard(R.string.app_name, R.drawable.editprofilepicicon),
    UserMgmt(R.string.user_mgmt, R.drawable.editprofilepicicon),
    MenuMgmt(R.string.menu_mgmt, R.drawable.editprofilepicicon),
    WaitlistMgmt(R.string.waitlist_mgmt, R.drawable.editprofilepicicon),
    OrderingMgmt(R.string.order_mgmt, R.drawable.editprofilepicicon),
    FeedbackMgmt(R.string.feedback_mgmt, R.drawable.editprofilepicicon)
}

@Composable
fun ApaPunAdaApp(
    navController: NavHostController = rememberNavController()
) {
    val authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController, startDestination = StartScreen.Introduction.name
    ) {
        composable(StartScreen.Introduction.name) {
            IntroductionPager(
                onLoginButtonClicked = { navController.navigate(StartScreen.Login.name) },
                onSignUpButtonClicked = { navController.navigate(StartScreen.SignUp.name) }
            )
        }

        composable(StartScreen.Login.name) {
            LoginScreen(
                onBackButtonClicked = { navController.navigateUp() },
                onLoginButtonClicked = { navController.navigate(StartScreen.Identify.name) },
                authViewModel = authViewModel,
                navController = navController
            )
        }

        composable(StartScreen.SignUp.name) {
            SignUpScreen(
                onBackButtonClicked = { navController.navigateUp() },
                onSignUpButtonClicked = { navController.navigate(StartScreen.Introduction.name) }
            )
        }

        composable(StartScreen.Identify.name) {
            if (authViewModel.userState.value.user.role == getEnumList(UserRole::class.java)[1]) {
                navController.navigate(StartScreen.Staff.name)
            } else {
                navController.navigate(StartScreen.User.name)
            }
        }

        composable(StartScreen.User.name) {
            UserNavigation(authViewModel = authViewModel)
        }

        composable(StartScreen.Staff.name) {
            StaffUI(authViewModel = authViewModel)
        }
    }
}

@Composable
fun UserNavigation(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = UserScreen.Home.name,
    ) {
        composable(UserScreen.Home.name) {
            HomeScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable(UserScreen.Waitlist.name) {
            WaitlistScreen(
                onBackButtonClicked = { navController.navigateUp() },
                authViewModel = authViewModel
            )
        }

        composable(UserScreen.Order.name) {
            OrderNav()
        }

        composable(UserScreen.Rewards.name) {
            RewardsScreenNavigation()
        }

        composable(UserScreen.More.name) {
            MoreScreen(navController = navController) // navigate another
        }
    }
}

@Composable
fun StaffUI(
    navController: NavHostController = rememberNavController(),
    authViewModel: AuthViewModel
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
                                    text = stringResource(screen.title),
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
                        HorizontalDivider(Modifier.padding(horizontal = 10.dp))
                    }
                }
            }
        },

    ) {
        Scaffold(
            topBar = { StaffAppBar(
                currentScreen = currentScreen,
                drawerState = drawerState,
                coroutineScope = coroutineScope,
                user = authViewModel.userState.value.user,
                navController = navController,
                authViewModel = authViewModel,
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

        composable(route = StaffScreen.UserMgmt.name){
            currentScreen(StaffScreen.UserMgmt)
            StaffUserScreen()
        }

        composable(route = StaffScreen.MenuMgmt.name){
            currentScreen(StaffScreen.MenuMgmt)
            StaffMenuScreen()
        }

        composable(route = StaffScreen.WaitlistMgmt.name){
            currentScreen(StaffScreen.WaitlistMgmt)
            StaffWaitlistScreen()
        }

        composable(route = StaffScreen.OrderingMgmt.name){
            currentScreen(StaffScreen.OrderingMgmt)
            StaffOrderScreen()
        }

        composable(route = StaffScreen.FeedbackMgmt.name){
            currentScreen(StaffScreen.FeedbackMgmt)
            StaffFeedbackScreen()
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ApaPunAdaAppPreview() {
//    StaffUI()
}