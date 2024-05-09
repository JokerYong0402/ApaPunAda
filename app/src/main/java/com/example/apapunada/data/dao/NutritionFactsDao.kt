package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.NutritionFacts
import kotlinx.coroutines.flow.Flow

@Dao
interface NutritionFactsDao {
    @Insert
    suspend fun insertNutritionFacts(nutritionFacts: NutritionFacts)

    @Update
    suspend fun updateNutritionFacts(nutritionFacts: NutritionFacts)

    @Delete
    suspend fun deleteNutritionFacts(nutritionFacts: NutritionFacts)

    @Query("SELECT * FROM `nutritionFacts`")
    fun getAllNutritionFacts(): Flow<List<NutritionFacts>>

    @Query("SELECT * FROM `nutritionFacts` WHERE nutritionFactsID = :id")
    fun getNutritionFactsByNutritionFactsId(id: Int): Flow<NutritionFacts>

    @Query("SELECT * FROM `nutritionFacts` WHERE foodDetailsID = :id")
    fun getNutritionFactsByFoodDetailsId(id: Int): Flow<NutritionFacts>
}