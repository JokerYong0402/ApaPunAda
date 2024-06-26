package com.example.apapunada.data

import android.content.Context
import com.example.apapunada.data.repository.FeedbackOfflineRepository
import com.example.apapunada.data.repository.FeedbackRepository
import com.example.apapunada.data.repository.FoodDetailsOfflineRepository
import com.example.apapunada.data.repository.FoodDetailsRepository
import com.example.apapunada.data.repository.MenuItemOfflineRepository
import com.example.apapunada.data.repository.MenuItemRepository
import com.example.apapunada.data.repository.NutritionFactsOfflineRepository
import com.example.apapunada.data.repository.NutritionFactsRepository
import com.example.apapunada.data.repository.OrderDetailsOfflineRepository
import com.example.apapunada.data.repository.OrderDetailsRepository
import com.example.apapunada.data.repository.OrderOfflineRepository
import com.example.apapunada.data.repository.OrderRepository
import com.example.apapunada.data.repository.UserOfflineRepository
import com.example.apapunada.data.repository.UserRepository
import com.example.apapunada.data.repository.VoucherOfflineRepository
import com.example.apapunada.data.repository.VoucherRepository
import com.example.apapunada.data.repository.WaitlistOfflineRepository
import com.example.apapunada.data.repository.WaitlistRepository

interface AppContainer {
    val feedbackRepository: FeedbackRepository
    val foodDetailsRepository: FoodDetailsRepository
    val menuItemRepository: MenuItemRepository
    val nutritionFactsRepository: NutritionFactsRepository
    val orderRepository: OrderRepository
    val orderDetailsRepository: OrderDetailsRepository
    val userRepository: UserRepository
    val voucherRepository: VoucherRepository
    val waitlistRepository: WaitlistRepository
}

class AppDataContainer(private val context: Context): AppContainer {

    override val feedbackRepository: FeedbackRepository by lazy {
        FeedbackOfflineRepository(ApaPunAdaDatabase.getDatabase(context).feedbackDao())
    }

    override val foodDetailsRepository: FoodDetailsRepository by lazy {
        FoodDetailsOfflineRepository(ApaPunAdaDatabase.getDatabase(context).foodDetailsDao())
    }

    override val menuItemRepository: MenuItemRepository by lazy {
        MenuItemOfflineRepository(ApaPunAdaDatabase.getDatabase(context).menuItemDao())
    }

    override val nutritionFactsRepository: NutritionFactsRepository by lazy {
        NutritionFactsOfflineRepository(ApaPunAdaDatabase.getDatabase(context).nutritionFactsDao())
    }

    override val orderRepository: OrderRepository by lazy {
        OrderOfflineRepository(ApaPunAdaDatabase.getDatabase(context).orderDao())
    }

    override val orderDetailsRepository: OrderDetailsRepository by lazy {
        OrderDetailsOfflineRepository(ApaPunAdaDatabase.getDatabase(context).orderDetailsDao())
    }

    override val userRepository: UserRepository by lazy {
        UserOfflineRepository(ApaPunAdaDatabase.getDatabase(context).userDao())
    }

    override val voucherRepository: VoucherRepository by lazy {
        VoucherOfflineRepository(ApaPunAdaDatabase.getDatabase(context).voucherDao())
    }

    override val waitlistRepository: WaitlistRepository by lazy {
        WaitlistOfflineRepository(ApaPunAdaDatabase.getDatabase(context).waitlistDao())
    }
}