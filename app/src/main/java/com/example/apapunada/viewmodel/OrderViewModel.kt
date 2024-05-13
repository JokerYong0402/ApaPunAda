package com.example.apapunada.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.dataclass.OrderDetails
import com.example.apapunada.data.repository.OrderDetailsRepository
import com.example.apapunada.data.repository.OrderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class OrderState(
    val order: Order = Order(),
    val isValid: Boolean = false,
    val errorMessage: String = ""
)

data class OrderListState(
    val isLoading: Boolean = false,
    val orderList: List<Order> = listOf(Order()),
    val errorMessage: String = ""
)

data class OrderDetailsListState(
    val isLoading: Boolean = false,
    val orderDetails: List<OrderDetails> = listOf(OrderDetails()),
    val isValid: Boolean = false,
    val errorMessage: String = ""
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
    Preparing("Preparing"),
    Ready("Ready"),
    Completed("Completed"),
    Cancelled("Cancelled"),
}

class OrderViewModel(
    private val orderRepository: OrderRepository,
    private val orderDetailsRepository: OrderDetailsRepository
): ViewModel() {

    private val _orderState = MutableStateFlow(OrderState())
    val orderState: StateFlow<OrderState> = _orderState.asStateFlow()

    private val _orderListState = MutableStateFlow(OrderListState())
    val orderListState: StateFlow<OrderListState> = _orderListState.asStateFlow()

    private val _orderDetailsListState = MutableStateFlow(OrderDetailsListState())
    val orderDetailsListState: StateFlow<OrderDetailsListState> = _orderDetailsListState.asStateFlow()

    fun loadAllOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.getAllOrdersStream()
                .map { OrderListState(isLoading = false, orderList = it) }
                .onStart { emit(OrderListState(isLoading = true)) }
                .catch {
                    emit(OrderListState(errorMessage = it.message.toString()))
                    Log.i("Order", "loadAllOrders: " + it.message.toString())
                }
                .collect { _orderListState.value = it }
        }
    }

    fun loadOrderByOrderId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.getOrderByOrderIdStream(id)
                .map { OrderState(order = it) }
                .catch {
                    emit(OrderState(errorMessage = it.message.toString()))
                    Log.i("Order", "loadOrderByOrderId: " + it.message.toString())
                }
                .collect { _orderState.value = it }
        }
    }

    fun loadOrderByUserId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.getOrderByUserIdStream(id)
                .map { OrderListState(isLoading = false, orderList = it) }
                .onStart { emit(OrderListState(isLoading = true)) }
                .catch {
                    emit(OrderListState(errorMessage = it.message.toString()))
                    Log.i("Order", "loadOrderByUserId: " + it.message.toString())
                }
                .collect { _orderListState.value = it }
        }
    }

    fun loadOrderDetailsByOrderId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderDetailsRepository.getOrderDetailsByOrderIdStream(id)
                .map { OrderDetailsListState(isLoading = false, orderDetails = it) }
                .onStart { emit(OrderDetailsListState(isLoading = true)) }
                .catch {
                    emit(OrderDetailsListState(errorMessage = it.message.toString()))
                    Log.i(
                        "OrderDetails", "loadOrderDetailsByOrderId: "
                                + it.message.toString()
                    )
                }
                .collect { _orderDetailsListState.value = it }
        }
    }

    fun loadOrderDetailsByMenuItemId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            orderDetailsRepository.getOrderDetailsByMenuItemIdStream(id)
                .map { OrderDetailsListState(isLoading = false, orderDetails = it) }
                .onStart { emit(OrderDetailsListState(isLoading = true)) }
                .catch {
                    emit(OrderDetailsListState(errorMessage = it.message.toString()))
                    Log.i(
                        "OrderDetails", "loadOrderDetailsByOrderId: " + it.message.toString()
                    )
                }
                .collect { _orderDetailsListState.value = it }
        }
    }

    private fun validateOrderInput(): Boolean {
        return with(_orderState.value.order) {
            method.isNotBlank() && paymentStatus.isNotBlank() && orderStatus.isNotBlank() // TODO
        }
    }

    private fun validateDetailsInput(): Boolean {
        var validation: Boolean = true

        for (detail in _orderDetailsListState.value.orderDetails) {
            validation = with(detail) {
                total.isNaN()
            }

            if (!validation) {
                break
            }
        }

        return validation
    }

    fun updateOrderState(order: Order) {
        _orderState.value = OrderState(order = order, isValid = validateOrderInput())
    }

    fun saveOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateOrderInput()) {
                try {
                    orderRepository.insertOrder(orderState.value.order)
                } catch (e: Exception) {
                    Log.i("Order", "saveOrder: " + e.message.toString())
                }
            }
        }
    }

    fun updateOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateOrderInput()) {
                try {
                    orderRepository.updateOrder(orderState.value.order)
                } catch (e: Exception) {
                    Log.i("Order", "updateOrder: " + e.message.toString())
                }
            }
        }
    }

    private suspend fun deleteOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateOrderInput()) {
                try {
                    orderRepository.deleteOrder(orderState.value.order)
                } catch (e: Exception) {
                    Log.i("Order", "deleteOrder: " + e.message.toString())
                }
            }
        }
    }

    fun saveDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateDetailsInput()) {
                try {
                    for (detail in orderDetailsListState.value.orderDetails) {
                        orderDetailsRepository.insertOrderDetails(detail)
                    }
                } catch (e: Exception) {
                    Log.i("OrderDetail", "saveOrderDetail: " + e.message.toString())
                }
            }
        }
    }

    fun updateDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateDetailsInput()) {
                try {
                    for (detail in orderDetailsListState.value.orderDetails) {
                        orderDetailsRepository.updateOrderDetails(detail)
                    }
                } catch (e: Exception) {
                    Log.i("OrderDetail", "updateOrderDetail: " + e.message.toString())
                }
            }
        }
    }

    private suspend fun deleteDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateDetailsInput()) {
                try {
                    for (detail in orderDetailsListState.value.orderDetails) {
                        orderDetailsRepository.deleteOrderDetails(detail)
                    }
                } catch (e: Exception) {
                    Log.i("OrderDetail", "deleteOrderDetail: " + e.message.toString())
                }
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
