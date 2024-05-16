package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.OrderDetails
import kotlinx.coroutines.flow.Flow

interface OrderDetailsRepository {

    fun getOrderDetailsByOrderDetailsIdStream(id: Int): Flow<OrderDetails>

    fun getOrderDetailsByMenuItemIdStream(id: Int): Flow<List<OrderDetails>>

    fun getOrderDetailsByOrderIdStream(id: Int): Flow<List<OrderDetails>>

    suspend fun deleteOrderDetails(orderDetails: OrderDetails)

    suspend fun updateOrderDetails(orderDetails: OrderDetails)

    suspend fun insertOrderDetails(orderDetails: OrderDetails)

}