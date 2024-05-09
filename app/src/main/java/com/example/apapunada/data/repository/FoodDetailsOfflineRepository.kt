package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.FoodDetailsDao
import com.example.apapunada.data.dataclass.FoodDetails
import kotlinx.coroutines.flow.Flow

class FoodDetailsOfflineRepository(private val foodDetailsDao: FoodDetailsDao): FoodDetailsRepository {
    override fun getFoodDetailsByMenuItemIdStream(id: Int): Flow<FoodDetails> = foodDetailsDao.getFoodDetailsByMenuItemId(id)

    override suspend fun insertFoodDetails(foodDetails: FoodDetails) = foodDetailsDao.insertFoodDetails(foodDetails)

    override suspend fun updateFoodDetails(foodDetails: FoodDetails) = foodDetailsDao.updateFoodDetails(foodDetails)

    override suspend fun deleteFoodDetails(foodDetails: FoodDetails) = foodDetailsDao.deleteFoodDetails(foodDetails)
}