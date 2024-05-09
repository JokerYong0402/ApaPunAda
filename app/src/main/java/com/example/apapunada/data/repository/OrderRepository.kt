package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getOrderByUserIdStream(id: Int): List<Flow<Order>>

    fun getOrderByOrderIdStream(id: Int): Flow<Order>

    fun getAllOrdersStream(): Flow<List<Order>>

    suspend fun deleteOrder(order: Order)

    suspend fun updateOrder(order: Order)

    suspend fun insertOrder(order: Order)
}