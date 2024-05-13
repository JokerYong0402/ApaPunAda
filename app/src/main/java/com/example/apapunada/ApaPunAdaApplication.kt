package com.example.apapunada

import android.app.Application
import com.example.apapunada.data.AppContainer
import com.example.apapunada.data.AppDataContainer
import com.example.apapunada.data.PrepopulateData
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ApaPunAdaApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)

        initializeDatabase()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun initializeDatabase() {
        val feedbackRepository = container.feedbackRepository
        val foodDetailsRepository = container.foodDetailsRepository
        val menuItemRepository = container.menuItemRepository
        val nutritionFactsRepository = container.nutritionFactsRepository
        val orderRepository = container.orderRepository
        val orderDetailsRepository = container.orderDetailsRepository
        val userRepository = container.userRepository
        val voucherRepository = container.voucherRepository
        val waitlistRepository = container.waitlistRepository

        GlobalScope.launch(Dispatchers.IO) {
            if (feedbackRepository.getAllFeedbacksStream().first().isEmpty()) {

                val initialUserList = PrepopulateData.users
                val initialWaitlistList = PrepopulateData.waitlist
                val initialFeedbackList = PrepopulateData.feedbackList
                val initialOrderList = PrepopulateData.orderList
                val initialMenuItemList = PrepopulateData.menuItemList
                val initialFoodDetailsList = PrepopulateData.foodDetailsList
                val initialNutritionFactsList = PrepopulateData.nutritionFactsList
                val initialOrderDetailsList = PrepopulateData.orderDetailsList
                val initialVoucherList = PrepopulateData.voucherList

                for(user in initialUserList) {
                    userRepository.insertUser(user)
                }
                for(waitlist in initialWaitlistList) {
                    waitlistRepository.insertWaitlist(waitlist)
                }
                for(feedback in initialFeedbackList) {
                    feedbackRepository.insertFeedback(feedback)
                }
                for(order in initialOrderList) {
                    orderRepository.insertOrder(order)
                }
                for(menuItem in initialMenuItemList) {
                    menuItemRepository.insertMenuItem(menuItem)
                }
                for(food in initialFoodDetailsList) {
                    foodDetailsRepository.insertFoodDetails(food)
                }
                for(fact in initialNutritionFactsList) {
                    nutritionFactsRepository.insertNutritionFacts(fact)
                }
                for(detail in initialOrderDetailsList) {
                    orderDetailsRepository.insertOrderDetails(detail)
                }
                for(voucher in initialVoucherList) {
                    voucherRepository.insertVoucher(voucher)
                }
            }
        }
    }
}