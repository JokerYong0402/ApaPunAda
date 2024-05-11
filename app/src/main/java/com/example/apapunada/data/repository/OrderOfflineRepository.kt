package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.OrderDao
import com.example.apapunada.data.dataclass.Order
import kotlinx.coroutines.flow.Flow

class OrderOfflineRepository(private val orderDao: OrderDao): OrderRepository {

    override fun getOrderByUserIdStream(id: Int): Flow<List<Order>> = orderDao.getOrderByUserId(id)

    override fun getOrderByOrderIdStream(id: Int): Flow<Order> = orderDao.getOrderByOrderId(id)

    override fun getAllOrdersStream(): Flow<List<Order>> = orderDao.getAllOrders()

    override suspend fun deleteOrder(order: Order) = orderDao.deleteOrder(order)

    override suspend fun updateOrder(order: Order) = orderDao.updateOrder(order)

    override suspend fun insertOrder(order: Order) = orderDao.insertOrder(order)
}