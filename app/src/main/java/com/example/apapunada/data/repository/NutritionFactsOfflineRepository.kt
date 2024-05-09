package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.NutritionFactsDao
import com.example.apapunada.data.dataclass.NutritionFacts
import kotlinx.coroutines.flow.Flow

class NutritionFactsOfflineRepository(private val nutritionFactsDao: NutritionFactsDao)
    : NutritionFactsRepository {

    override fun getNutritionFactsByFoodDetailsIdStream(id: Int): Flow<NutritionFacts>
        = nutritionFactsDao.getNutritionFactsByNutritionFactsId(id)

    override fun getNutritionFactsByNutritionFactsIdStream(id: Int): Flow<NutritionFacts>
        = nutritionFactsDao.getNutritionFactsByNutritionFactsId(id)

    override fun getAllNutritionFactsStream(): Flow<List<NutritionFacts>>
            = nutritionFactsDao.getAllNutritionFacts()

    override suspend fun deleteNutritionFacts(nutritionFacts: NutritionFacts)
        = nutritionFactsDao.deleteNutritionFacts(nutritionFacts)

    override suspend fun updateNutritionFacts(nutritionFacts: NutritionFacts)
        = nutritionFactsDao.updateNutritionFacts(nutritionFacts)

    override suspend fun insertNutritionFacts(nutritionFacts: NutritionFacts)
        = nutritionFactsDao.insertNutritionFacts(nutritionFacts)
}