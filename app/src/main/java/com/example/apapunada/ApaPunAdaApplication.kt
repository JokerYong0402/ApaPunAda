package com.example.apapunada

import android.app.Application
import android.content.Context
import com.example.apapunada.data.AppContainer
import com.example.apapunada.data.AppDataContainer
import com.example.apapunada.data.PrepopulateData
import com.example.apapunada.ui.components.drawableResourceToByteArray
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ApaPunAdaApplication: Application() {

    lateinit var container: AppContainer
    lateinit var context: Context

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        context = applicationContext

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
            if (userRepository.getAllUsersStream().first().isEmpty()) {

                val initialUserList = PrepopulateData.users
                val initialWaitlistList = PrepopulateData.waitlist
                val initialFeedbackList = PrepopulateData.feedbackList
                val initialOrderList = PrepopulateData.orderList
                val initialMenuItemList = PrepopulateData.menuItemList
                val initialFoodDetailsList = PrepopulateData.foodDetailsList
                val initialNutritionFactsList = PrepopulateData.nutritionFactsList
                val initialOrderDetailsList = PrepopulateData.orderDetailsList
                val initialVoucherList = PrepopulateData.voucherList

                val initialMenuItemImageList = PrepopulateData.menuListImage

                for(user in initialUserList) {
                    userRepository.insertUser(user.copy(
                        image = drawableResourceToByteArray(context, R.drawable.profile_image)!!
                    ))
                }
                for(waitlist in initialWaitlistList) {
                    waitlistRepository.insertWaitlist(waitlist)
                }
                for(feedback in initialFeedbackList) {
                    feedbackRepository.insertFeedback(feedback.copy(
                        images = drawableResourceToByteArray(context, R.drawable.chickenkatsu)!!
                    ))
                }
                for(order in initialOrderList) {
                    orderRepository.insertOrder(order)
                }

                initialMenuItemList.forEachIndexed { i, menuItem ->
                    menuItemRepository.insertMenuItem(menuItem.copy(
                        image = drawableResourceToByteArray(context, initialMenuItemImageList[i])!!
                    ))
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