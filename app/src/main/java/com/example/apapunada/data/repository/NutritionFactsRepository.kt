package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.NutritionFacts
import kotlinx.coroutines.flow.Flow

interface NutritionFactsRepository {

    fun getNutritionFactsByFoodDetailsIdStream(id: Int): Flow<NutritionFacts>

    fun getNutritionFactsByNutritionFactsIdStream(id: Int): Flow<NutritionFacts>

    fun getAllNutritionFactsStream(): Flow<List<NutritionFacts>>

    suspend fun deleteNutritionFacts(nutritionFacts: NutritionFacts)

    suspend fun updateNutritionFacts(nutritionFacts: NutritionFacts)

    suspend fun insertNutritionFacts(nutritionFacts: NutritionFacts)
}