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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
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
import com.example.apapunada.ui.users.AboutUsScreen
import com.example.apapunada.ui.users.DeleteProfileScreen
import com.example.apapunada.ui.users.EditProfileScreen
import com.example.apapunada.ui.users.FeedbackScreen
import com.example.apapunada.ui.users.FeedbackSuccessScreen
import com.example.apapunada.ui.users.FoodDetailScreen
import com.example.apapunada.ui.users.FoodListScreen
import com.example.apapunada.ui.users.HomeScreen
import com.example.apapunada.ui.users.MenuScreen
import com.example.apapunada.ui.users.MoreScreen
import com.example.apapunada.ui.users.OrderCartScreen
import com.example.apapunada.ui.users.OrderCheckoutScreen
import com.example.apapunada.ui.users.OrderCustomizeScreen
import com.example.apapunada.ui.users.OrderFailedScreen
import com.example.apapunada.ui.users.OrderMenuScreen
import com.example.apapunada.ui.users.OrderOptionScreen
import com.example.apapunada.ui.users.OrderPaymentScreen
import com.example.apapunada.ui.users.OrderSuccessScreen
import com.example.apapunada.ui.users.ProfileScreen
import com.example.apapunada.ui.users.RewardsScreen
import com.example.apapunada.ui.users.SignUpScreen
import com.example.apapunada.ui.users.VoucherDetails
import com.example.apapunada.ui.users.VoucherRedeem
import com.example.apapunada.ui.users.WaitlistScreen
import com.example.apapunada.viewmodel.AuthViewModel
import com.example.apapunada.viewmodel.OrderViewModel
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

enum class OrderScreen(@StringRes val routeResId: Int) {
    Option(R.string.order_option),
    Menu(R.string.order_menu),
    Customization(R.string.order_customize),
    Cart(R.string.order_cart),
    Checkout(R.string.order_checkout),
    Payment(R.string.order_payment),
    Success(R.string.order_success),
    Failed(R.string.order_failed),
}
enum class MoreScreen(@StringRes val title: Int) {
    More(R.string.more),
    Profile(R.string.profile),
    Menu(R.string.menu),
    Feedback(R.string.feedback),
    AboutUs(R.string.about_us),
    OrderHistory(R.string.history),
    Faq(R.string.faq),
    Tnc(R.string.tnc)
}

enum class ProfileNav(@StringRes val title: Int){
    Profile(R.string.profile),
    EditProfile(R.string.edit_profile),
    DeleteProfile(R.string.delete_profile)
}

enum class FeedbackNav(@StringRes val title: Int){
    Feedback(R.string.feedback),
    FeedbackSuccess(R.string.feedback_success),
}

enum class MenuNav(@StringRes val title: Int){
    Menu(R.string.menu),
    FoodList(R.string.food_list),
    FoodDetail(R.string.food_details)
}

@Composable
fun ApaPunAdaApp(
    navController: NavHostController = rememberNavController()
) {
    val authViewModel: AuthViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory)

    NavHost(
        navController = navController,
        startDestination = StartScreen.Introduction.name,
        route = "Start"
    ) {

        // Start: Introduction
        composable(StartScreen.Introduction.name) {
            IntroductionPager(
                onLoginButtonClicked = { navController.navigate(StartScreen.Login.name) },
                onSignUpButtonClicked = { navController.navigate(StartScreen.SignUp.name) }
            )
        }

        // Start: Login
        composable(StartScreen.Login.name) {
            LoginScreen(
                onBackButtonClicked = { navController.navigateUp() },
                onLoginButtonClicked = { navController.navigate(StartScreen.Identify.name) },
                authViewModel = authViewModel,
                navController = navController
            )
        }

        // Start: Sign Up
        composable(StartScreen.SignUp.name) {
            SignUpScreen(
                onBackButtonClicked = { navController.navigateUp() },
                onSignUpButtonClicked = { navController.navigate(StartScreen.Introduction.name) }
            )
        }

        // Start: To identify where user or staff
        composable(StartScreen.Identify.name) {
            if (authViewModel.userState.value.user.role == getEnumList(UserRole::class.java)[1]) {
                navController.navigate(StartScreen.Staff.name)
            } else {
                navController.navigate("UserLoggedIn")
            }
        }


        // User: Home
        navigation(
            startDestination = UserScreen.Home.name,
            route = "UserLoggedIn"
        ) {
            composable(UserScreen.Home.name) {
                HomeScreen(
                    navController = navController,
                    authViewModel = authViewModel
                )
            }

            // User: Waitlist
            composable(UserScreen.Waitlist.name) {
                WaitlistScreen(
                    onBackButtonClicked = { navController.navigate("UserLoggedIn") }
                )
            }



            // User: Order
            navigation(
                startDestination = OrderScreen.Option.name,
                route = "Ordering"
            ) {
                composable(OrderScreen.Option.name) {
                    OrderOptionScreen(
                        onBackButtonClicked = { navController.navigate("UserLoggedIn") },
                        onNextButtonClicked = { navController.navigate(OrderScreen.Menu.name) },
                        viewModel = orderViewModel
                    )
                }

                var menuItemID: Int = 0
                composable(OrderScreen.Menu.name) {
                    OrderMenuScreen(
                        onBackButtonClicked = { navController.navigate(OrderScreen.Option.name) },
                        onAddButtonClicked = {
                            menuItemID = it
                            navController.navigate(OrderScreen.Customization.name)
                        },
                        onCheckoutButtonClicked = { navController.navigate(OrderScreen.Cart.name) },
                        orderViewModel = orderViewModel
                    )
                }

                composable(OrderScreen.Customization.name) {
                    OrderCustomizeScreen(
                        orderViewModel = orderViewModel,
                        menuItemID = menuItemID,
                        onButtonClicked = { navController.navigate(OrderScreen.Menu.name) },
                    )
                }

                composable(OrderScreen.Cart.name) {
                    OrderCartScreen(
                        onBackButtonClicked = { navController.navigate(OrderScreen.Menu.name) },
                        onCheckoutClicked = { navController.navigate(OrderScreen.Checkout.name) },
                        orderViewModel = orderViewModel
                    )
                }

                composable(OrderScreen.Checkout.name) {
                    OrderCheckoutScreen(
                        onBackButtonClicked = { navController.navigate(OrderScreen.Cart.name) },
                        onPayButtonClicked = { navController.navigate(OrderScreen.Payment.name) },
                        orderViewModel = orderViewModel
                    )
                }

                var paymentMethod: String = ""
                composable(OrderScreen.Payment.name) {
                    OrderPaymentScreen(
                        onPayButtonClicked = {
                            paymentMethod = it
                            navController.navigate(OrderScreen.Success.name)
                        },
                        onCancelButtonClicked = {
                            paymentMethod = it
                            navController.navigate(OrderScreen.Failed.name)
                        },
                        orderViewModel = orderViewModel
                    )
                }

                composable(OrderScreen.Success.name) {
                    OrderSuccessScreen(
                        onBackToHomeClicked = { navController.navigate("UserLoggedIn") },
                        onOrderDetailsClicked = { navController.navigate(MoreScreen.OrderHistory.name)}, // to history
                        paymentMethod = paymentMethod,
                        orderViewModel = orderViewModel,
                    )
                }

                composable(OrderScreen.Failed.name) {
                    OrderFailedScreen(
                        onBackToHomeClicked = { navController.navigate("UserLoggedIn")  },
                        onTryAgainClicked = { navController.navigate(OrderScreen.Payment.name) },
                        paymentMethod = paymentMethod,
                        orderViewModel = orderViewModel,
                    )
                }
            }



            // User: Rewards
            navigation(
                startDestination = "RewardsScreen",
                route = "Rewarding"
            ) {

                composable("RewardsScreen") {
                    RewardsScreen(onBackButtonClicked = { navController.navigateUp() },
                        onRedeem = { drawableId, voucherRM -> navController.navigate("VoucherRedeem/$drawableId/$voucherRM") },
                        onDetails = { drawableId, voucherRM -> navController.navigate("VoucherDetails/$drawableId/$voucherRM") },
                        userId = 6)
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
                        onDetails = { drawableId, voucherRM -> navController.navigate("VoucherDetails/$drawableId/$voucherRM") },
                        painterResource(drawableId),
                        voucherRM = voucherRM,
                        userId = 6
                    )
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
                        navController = navController
                    )
                }
            }

            // User: More
            navigation(
                startDestination = MoreScreen.More.name,
                route = "UserMore"
            ) {

                composable(MoreScreen.More.name) {
                    MoreScreen(navController = navController, authViewModel = authViewModel)
                }



                // More: Profile
                navigation(
                    startDestination = ProfileNav.Profile.name,
                    route = "UserProfile"
                ){
                    composable(ProfileNav.Profile.name){
                        ProfileScreen(
                            onEdit = { navController.navigate(ProfileNav.EditProfile.name)},
                            onDelete = { navController.navigate(ProfileNav.DeleteProfile.name)},
                            onLogin = { navController.navigate("Start")},
                            onBackClicked = { navController.popBackStack() }
                        )

                    }
                    composable(ProfileNav.EditProfile.name){
                        EditProfileScreen(
                            onBackButtonClicked = { navController.popBackStack() },
                            onProfile = { navController.popBackStack() }
                        )
                    }
                    composable(ProfileNav.DeleteProfile.name){
                        DeleteProfileScreen(
                            onBackButtonClicked = { navController.popBackStack() },
                            onLogin = { navController.navigate("Start") }
                        )
                    }

                }


                // More: Menu
                navigation(
                    startDestination = MoreScreen.Menu.name,
                    route = "UserMenu"
                ) {
                    var menuType: String = ""
                    var menuId: Int = 0
                    composable(MenuNav.Menu.name) {
                        MenuScreen(
                            onBackClicked = { navController.navigate("UserMore") },
                            onMenuTypeClick = {
                                menuType = it
                                navController.navigate(MenuNav.FoodList.name)
                            },
                            onDish = {
                                menuId = it
                                navController.navigate(MenuNav.FoodDetail.name)
                            },
                        )
                    }
                    composable(MenuNav.FoodList.name) {
                        FoodListScreen(
                            onBackButtonClicked = { navController.navigate("UserMenu") },
                            menuType = menuType,
                            onDish = {
                                menuId = it
                                navController.navigate(MenuNav.FoodDetail.name)
                            }
                        )

                    }
                    composable(MenuNav.FoodDetail.name) {
                        FoodDetailScreen(
                            onBackButtonClicked = { navController.navigateUp() },
                            onOrder = { navController.navigate(UserScreen.Order.name) },
                            currentDishId = menuId
                        )
                    }
                }

                // More: Feedback
                navigation(
                    startDestination = FeedbackNav.Feedback.name,
                    route = "UserFeedback"
                ) {
                    composable(FeedbackNav.Feedback.name){
                        FeedbackScreen(
                            onSubmit = { navController.navigate(FeedbackNav.FeedbackSuccess.name)},
                            onBackClicked = { navController.navigate("UserMore") }
                        )
                    }

                    composable(FeedbackNav.FeedbackSuccess.name) {
                        FeedbackSuccessScreen(
                            onClick = { navController.navigate(UserScreen.Home.name)}
                        )
                    }
                }

                // More: About Us
                composable(MoreScreen.AboutUs.name) {
                    AboutUsScreen(
                        onBackClicked = { navController.navigateUp() },
                        onFeedbackClicked = { navController.navigate("UserFeedback") },
                        onOrderClicked = { navController.navigate("Ordering") }
                    )
                }

                // More: Order History
                composable(MoreScreen.OrderHistory.name) {
                    MoreScreen(navController = navController, authViewModel = authViewModel)
                }

                // More: Faq
                composable(MoreScreen.Faq.name) {
                    MoreScreen(navController = navController, authViewModel = authViewModel)
                }

                // More: Tnc
                composable(MoreScreen.Tnc.name) {
                    MoreScreen(navController = navController, authViewModel = authViewModel)
                }
            }
        }




        composable(StartScreen.Staff.name) {
            StaffUI(authViewModel = authViewModel)
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
                                navController.navigate(screen.name)
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