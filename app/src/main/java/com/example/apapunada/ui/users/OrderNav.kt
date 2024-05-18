package com.example.apapunada.ui.users

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apapunada.R
import com.example.apapunada.ui.AppViewModelProvider
import com.example.apapunada.viewmodel.OrderViewModel

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

@Composable
fun OrderNav(
    navController: NavHostController = rememberNavController(),
    orderViewModel: OrderViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    NavHost(navController, startDestination = OrderScreen.Option.name) {
        composable(OrderScreen.Option.name) {
            OrderOptionScreen(
                onBackButtonClicked = { navController.navigateUp() },
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
                onBackToHomeClicked = {  }, // to home
                onOrderDetailsClicked = {  }, // to history
                paymentMethod = paymentMethod,
                orderViewModel = orderViewModel,
            )
        }

        composable(OrderScreen.Failed.name) {
            OrderFailedScreen(
                onBackToHomeClicked = {  }, // to home
                onTryAgainClicked = { navController.navigate(OrderScreen.Payment.name) },
                paymentMethod = paymentMethod,
                orderViewModel = orderViewModel,
            )
        }
    }
}