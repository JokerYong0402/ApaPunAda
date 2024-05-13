package com.example.apapunada.ui

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.apapunada.ApaPunAdaApplication
import com.example.apapunada.viewmodel.FeedbackViewModel
import com.example.apapunada.viewmodel.MenuItemViewModel
import com.example.apapunada.viewmodel.OrderViewModel
import com.example.apapunada.viewmodel.UserViewModel
import com.example.apapunada.viewmodel.WaitlistViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {

        initializer {
            FeedbackViewModel(apaPunAdaApplication().container.feedbackRepository)
        }

        initializer {
            MenuItemViewModel(
                apaPunAdaApplication().container.menuItemRepository,
                apaPunAdaApplication().container.foodDetailsRepository,
                apaPunAdaApplication().container.nutritionFactsRepository,
            )
        }

        initializer {
            OrderViewModel(
                apaPunAdaApplication().container.orderRepository,
                apaPunAdaApplication().container.orderDetailsRepository,
            )
        }

        initializer {
            UserViewModel(apaPunAdaApplication().container.userRepository)
        }

        initializer {
            WaitlistViewModel(apaPunAdaApplication().container.waitlistRepository)
        }
    }
}

fun CreationExtras.apaPunAdaApplication(): ApaPunAdaApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as ApaPunAdaApplication)