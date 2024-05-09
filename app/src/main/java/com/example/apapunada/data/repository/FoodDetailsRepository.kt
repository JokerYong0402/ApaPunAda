package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.FoodDetails
import kotlinx.coroutines.flow.Flow

interface FoodDetailsRepository {
    fun getFoodDetailsByMenuItemIdStream(id: Int): Flow<FoodDetails>

    suspend fun insertFoodDetails(foodDetails: FoodDetails)

    suspend fun updateFoodDetails(foodDetails: FoodDetails)

    suspend fun deleteFoodDetails(foodDetails: FoodDetails)
}