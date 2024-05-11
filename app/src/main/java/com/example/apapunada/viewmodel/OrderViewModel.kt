package com.example.apapunada.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.dataclass.OrderDetails
import com.example.apapunada.data.repository.OrderDetailsRepository
import com.example.apapunada.data.repository.OrderRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class OrderState(
    val order: Order = Order(),
    val isValid: Boolean = false
)

data class OrderListState(
    val isLoading: Boolean = false,
    val orderList: List<Order> = listOf(Order()),
    val errorMessage: String = ""
)

data class OrderDetailsListState(
    val isLoading: Boolean = false,
    val orderDetails: List<OrderDetails> = listOf(OrderDetails()),
    val errorMessage: String = "",
    val isValid: Boolean = false
)

enum class OrderMethod(method: String) {
    Dine("Dine-in"),
    Takeaway("Takeaway"),
    Delivery("Delivery")

}

enum class PaymentStatus(name: String) {
    Successful("Successful"),
    Unsuccessful("Unsuccessful")
}

enum class OrderStatus(name: String) {
    Active("Active"),
    Disabled("Disabled"),
    Deleted("Deleted"),
}

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val orderDetailsRepository: OrderDetailsRepository
): ViewModel() {

    var orderState by mutableStateOf(OrderState())
        private set

    var orderListState by mutableStateOf(OrderListState())
        private set

    var orderDetailsListState by mutableStateOf(OrderDetailsListState())
        private set

    fun loadAllOrders() {
        orderRepository.getAllOrdersStream()
            .map { OrderListState(isLoading = false, orderList = it) }
            .onStart { emit(OrderListState(isLoading = true)) }
            .catch {
                emit(OrderListState(errorMessage = it.message.toString()))
                Log.i("Order", "loadAllOrders: " + it.message.toString())
            }
    }

    fun loadOrderByOrderId(id: Int) {
        orderRepository.getOrderByOrderIdStream(id)
            .map { OrderState(order = it) }
            .catch { Log.i("Order", "loadOrderByOrderId: " + it.message.toString()) }
    }

    fun loadOrderByUserId(id: Int) {
        orderRepository.getOrderByUserIdStream(id)
            .map { OrderListState(isLoading = false, orderList = it) }
            .onStart { emit(OrderListState(isLoading = true)) }
            .catch {
                emit(OrderListState(errorMessage = it.message.toString()))
                Log.i("Order", "loadOrderByUserId: " + it.message.toString())
            }
    }

    fun loadOrderDetailsByOrderId(id: Int) {
        orderDetailsRepository.getOrderDetailsByOrderIdStream(id)
            .map { OrderDetailsListState(isLoading = false, orderDetails = it) }
            .onStart { emit(OrderDetailsListState(isLoading = true)) }
            .catch {
                emit(OrderDetailsListState(errorMessage = it.message.toString()))
                Log.i(
                    "OrderDetails", "loadOrderDetailsByOrderId: " + it.message.toString()
                )
            }
    }

    fun loadOrderDetailsByMenuItemId(id: Int) {
        orderDetailsRepository.getOrderDetailsByMenuItemIdStream(id)
            .map { OrderDetailsListState(isLoading = false, orderDetails = it) }
            .onStart { emit(OrderDetailsListState(isLoading = true)) }
            .catch {
                emit(OrderDetailsListState(errorMessage = it.message.toString()))
                Log.i(
                    "OrderDetails", "loadOrderDetailsByOrderId: " + it.message.toString()
                )
            }
    }

    private fun validateOrderInput(uiState: Order = orderState.order): Boolean {
        return with(uiState) {
            method.isNotBlank() && paymentStatus.isNotBlank() && orderStatus.isNotBlank() // TODO
        }
    }

    private fun validateDetailsInput(
        uiState: List<OrderDetails> = orderDetailsListState.orderDetails
    ): Boolean {
        var validation = true
        for (detail in uiState) {
            with(detail) {
                validation = total.isNaN()
            }

            if (!validation) {
                break
            }
        }

        return validation
    }

    fun updateOrderState(order: Order) {
        orderState = OrderState(order = order, isValid = validateOrderInput(order))
    }

    fun updateOrderDetailsListState(orderDetails: List<OrderDetails>) {
        orderDetailsListState = OrderDetailsListState(
            orderDetails = orderDetails, isValid = validateDetailsInput(orderDetails)
        )
    }

    suspend fun saveOrder() {
        if (validateOrderInput()) {
            try {
                orderRepository.insertOrder(orderState.order)
            } catch (e: Exception) {
                Log.i("Order", "saveOrder: " + e.message.toString())
            }
        }
    }

    suspend fun updateOrder() {
        if (validateOrderInput()) {
            try {
                orderRepository.updateOrder(orderState.order)
            } catch (e: Exception) {
                Log.i("Order", "updateOrder: " + e.message.toString())
            }
        }
    }

    suspend fun deleteOrder() {
        if (validateOrderInput()) {
            try {
                orderRepository.deleteOrder(orderState.order)
            } catch (e: Exception) {
                Log.i("Order", "deleteOrder: " + e.message.toString())
            }
        }
    }

    suspend fun saveDetail() {
        if (validateDetailsInput()) {
            try {
                for (detail in orderDetailsListState.orderDetails) {
                    orderDetailsRepository.insertOrderDetails(detail)
                }
            } catch (e: Exception) {
                Log.i("OrderDetail", "saveOrderDetail: " + e.message.toString())
            }
        }
    }

    suspend fun updateDetail() {
        if (validateDetailsInput()) {
            try {
                for (detail in orderDetailsListState.orderDetails) {
                    orderDetailsRepository.updateOrderDetails(detail)
                }
            } catch (e: Exception) {
                Log.i("OrderDetail", "updateOrderDetail: " + e.message.toString())
            }
        }
    }

    suspend fun deleteDetail() {
        if (validateDetailsInput()) {
            try {
                for (detail in orderDetailsListState.orderDetails) {
                    orderDetailsRepository.deleteOrderDetails(detail)
                }
            } catch (e: Exception) {
                Log.i("OrderDetail", "deleteOrderDetail: " + e.message.toString())
            }
        }
    }

}

//    private val _orderState = MutableStateFlow(OrderState())
//    val orderState: StateFlow<OrderUiState> = _orderState.asStateFlow()
//
//    private val _orderListState = MutableStateFlow(OrderListState())
//    val orderListState: StateFlow<OrderListState> = _orderListState.asStateFlow()
//
//    private val _orderDetailsListState = MutableStateFlow(OrderDetailsListState())
//    val orderDetailsListState: StateFlow<OrderDetailsListState> = _orderDetailsListState.asStateFlow()
//
//    private fun validateInput(uiState: OrderUiState = OrderState()): Boolean {
//        val validation = with(uiState.order) {
//            method.isNotBlank() && paymentStatus.isNotBlank() && orderStatus.isNotBlank()
//        }
//
//        if (validation) {
//            _orderState.update { it.copy(isValid = true) }
//        } else {
//            _orderState.update { it.copy(isValid = false) }
//        }
//
//        return uiState.isValid
//    }
//
//    fun getAllOrders() {
//        viewModelScope.launch {
//            try {
//                val orders = orderRepository.getAllOrdersStream()
//                _orderListState.update { it.copy(isLoading = false, orderList = orders) }
//            } catch (e: Exception) {
//                _orderListState.update { it.copy(isLoading = false, errorMessage = e.message.toString()) }
//            }
//        }
//    }
//
//    fun getOrderByOrderId(id: Int) {
//        viewModelScope.launch {
//            val order = orderRepository.getOrderByOrderIdStream(id)
//            _orderState.update { it.copy(order = order) }
//        }
//    }
//
//    fun getOrderByUserId(id: Int) {
//        viewModelScope.launch {
//            val order = orderRepository.getOrderByUserIdStream(id)
//            _orderListState.update { it.copy(orderList = order) }
//        }
//    }
//
//    fun saveOrder(orderState: OrderUiState) {
//        viewModelScope.launch {
//            try {
//                val order = orderState.order
//
//                orderRepository.insertOrder(
//                    Order(
//                        userID = order.userID,
//                        method = order.method,
//                        amount = order.amount, // TODO private fun to calculate
//                        dateTime = order.dateTime,
//                        paymentStatus = order.paymentStatus,
//                        orderStatus = order.orderStatus
//                    )
//                )
//            } catch (e: Exception) {
//                Log.i("Order", "saveOrder: " + e.message.toString())
//            }
//        }
//    }
//
//    fun updateOrder(orderState: OrderUiState) {
//        viewModelScope.launch {
//            try {
//                val order = orderState.order
//
//                orderRepository.insertOrder(
//                    Order(
//                        userID = order.userID,
//                        method = order.method,
//                        amount = order.amount,
//                        dateTime = order.dateTime,
//                        paymentStatus = order.paymentStatus,
//                        orderStatus = order.orderStatus
//                    )
//                )
//            } catch (e: Exception) {
//                Log.i("Order", "updateOrder: " + e.message.toString())
//            }
//        }
//    }
//
//    fun deleteOrder(orderState: OrderUiState) {
//        viewModelScope.launch {
//            try {
//                val order = orderState.order
//
//                orderRepository.deleteOrder(
//                    Order(
//                        userID = order.userID,
//                        method = order.method,
//                        amount = order.amount,
//                        dateTime = order.dateTime,
//                        paymentStatus = order.paymentStatus,
//                        orderStatus = order.orderStatus
//                    )
//                )
//            } catch (e: Exception) {
//                Log.i("Order", "deleteOrder: " + e.message.toString())
//            }
//        }
//    }
