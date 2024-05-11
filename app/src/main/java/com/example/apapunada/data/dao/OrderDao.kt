package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.Order
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert
    suspend fun insertOrder(order: Order)

    @Update
    suspend fun updateOrder(order: Order)

    @Delete
    suspend fun deleteOrder(order: Order)

    @Query("SELECT * FROM `order`")
    fun getAllOrders(): Flow<List<Order>>

    @Query("SELECT * FROM `order` WHERE orderID = :id")
    fun getOrderByOrderId(id: Int): Flow<Order>

    @Query("SELECT * FROM `order` WHERE userID = :id")
    fun getOrderByUserId(id: Int): Flow<List<Order>>
}