package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.OrderDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDetailsDao {
    @Insert
    suspend fun insertOrderDetails(orderDetails: OrderDetails)

    @Update
    suspend fun updateOrderDetails(orderDetails: OrderDetails)

    @Delete
    suspend fun deleteOrderDetails(orderDetails: OrderDetails)

//    @Query("SELECT * FROM `orderDetails`")
//    fun getAllOrderDetails(): Flow<List<OrderDetails>>

    @Query("SELECT * FROM `orderdetails` WHERE orderID = :id")
    fun getOrderDetailsByOrderId(id: Int): Flow<List<OrderDetails>>

    @Query("SELECT * FROM `orderdetails` WHERE menuItemID = :id")
    fun getOrderDetailsByMenuItemId(id: Int): Flow<List<OrderDetails>>
}