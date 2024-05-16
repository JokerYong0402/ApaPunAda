package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.OrderDetailsDao
import com.example.apapunada.data.dataclass.OrderDetails
import kotlinx.coroutines.flow.Flow

class OrderDetailsOfflineRepository(private val orderDetailsDao: OrderDetailsDao)
    : OrderDetailsRepository {

    override fun getOrderDetailsByOrderDetailsIdStream(id: Int): Flow<OrderDetails>
        = orderDetailsDao.getOrderDetailsByOrderDetailsId(id)

    override fun getOrderDetailsByMenuItemIdStream(id: Int): Flow<List<OrderDetails>>
        = orderDetailsDao.getOrderDetailsByMenuItemId(id)

    override fun getOrderDetailsByOrderIdStream(id: Int): Flow<List<OrderDetails>>
        = orderDetailsDao.getOrderDetailsByOrderId(id)

    override suspend fun deleteOrderDetails(orderDetails: OrderDetails)
        = orderDetailsDao.deleteOrderDetails(orderDetails)

    override suspend fun updateOrderDetails(orderDetails: OrderDetails)
        = orderDetailsDao.updateOrderDetails(orderDetails)

    override suspend fun insertOrderDetails(orderDetails: OrderDetails)
        = orderDetailsDao.insertOrderDetails(orderDetails)
}