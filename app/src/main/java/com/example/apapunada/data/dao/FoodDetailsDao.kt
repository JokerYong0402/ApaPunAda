package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.FoodDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDetailsDao {

    @Insert
    suspend fun insertFoodDetails(foodDetails: FoodDetails)

    @Update
    suspend fun updateFoodDetails(foodDetails: FoodDetails)

    @Delete
    suspend fun deleteFoodDetails(foodDetails: FoodDetails)

//    @Query("SELECT * FROM `foodDetails`")
//    fun getAllFoodDetails(): Flow<List<FoodDetails>>

    @Query("SELECT * FROM `foodDetails` WHERE menuItemID = :id")
    fun getFoodDetailsByMenuItemId(id: Int): Flow<FoodDetails>
}