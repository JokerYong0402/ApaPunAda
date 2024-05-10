package com.example.apapunada.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.Order
import com.example.apapunada.data.repository.OrderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OrderUiState(
    val order: Order = Order(),
    val isValid: Boolean = true
)

data class OrderListState(
    val isLoading: Boolean = true,
    val orderList: List<Order> = listOf(Order()),
    val listSize: Int = 0,
    val errorMessage: String = ""
)

enum class OrderMethod(method: String) {
    Dine("Dine-in"),
    Takeaway("Takeaway"),
    Delivery("Delivery")

}

enum class PaymentStatus {
    Successful,
    Unsuccessful
}

enum class OrderStatus {
    Active,
    Disabled,
    Deleted,
}

class OrderViewModel(private val orderRepository: OrderRepository): ViewModel() {
    private val _orderUiState = MutableStateFlow(OrderUiState())
    val orderUiState: StateFlow<OrderUiState> = _orderUiState.asStateFlow()

    private val _orderListState = MutableStateFlow(OrderListState())
    val orderListState: StateFlow<OrderListState> = _orderListState.asStateFlow()

    private fun validateInput(uiState: OrderUiState = OrderUiState()): Boolean {
        val validation = with(uiState.order) {
            method.isNotBlank() && paymentStatus.isNotBlank() && orderStatus.isNotBlank()
        }

        if (validation) {
            _orderUiState.update { it.copy(isValid = true) }
        } else {
            _orderUiState.update { it.copy(isValid = false) }
        }

        return uiState.isValid
    }

    private fun getOrderListOrder() {
        viewModelScope.launch {
            val size = orderListState.value.orderList.count()
            _orderListState.update { it.copy(listSize = size) }
        }
    }

    fun loadAllOrder() {
        viewModelScope.launch {
            try {
                val orders = orderRepository.getAllOrdersStream()
                _orderListState.update { it.copy(isLoading = false, orderList = orders) }
                getOrderListOrder()
            } catch (e: Exception) {
                _orderListState.update { it.copy(isLoading = false, errorMessage = e.message.toString()) }
            }
        }
    }

    fun loadOrderByOrderId(id: Int) {
        viewModelScope.launch {
            val order = orderRepository.getOrderByOrderIdStream(id)
            _orderUiState.update { it.copy(order = order) }
        }
    }

    fun loadOrderByUserId(id: Int) {
        viewModelScope.launch {
            val order = orderRepository.getOrderByUserIdStream(id)
            _orderListState.update { it.copy(orderList = order) }
        }
    }

    fun saveOrder(orderUiState: OrderUiState) {
        viewModelScope.launch {
            try {
                val order = orderUiState.order.first()

                orderRepository.insertOrder(
                    Order(
                        userID = order.userID,
                        method = order.method,
                        amount = order.amount,
                        dateTime = order.dateTime,
                        paymentStatus = order.paymentStatus,
                        orderStatus = order.orderStatus
                    )
                )
            } catch (e: Exception) {
                Log.i("Order", "saveOrder: " + e.message.toString())
            }
        }
    }

    fun updateOrder(orderUiState: OrderUiState) {
        viewModelScope.launch {
            try {
                val order = orderUiState.order.first()

                orderRepository.insertOrder(
                    Order(
                        userID = order.userID,
                        method = order.method,
                        amount = order.amount,
                        dateTime = order.dateTime,
                        paymentStatus = order.paymentStatus,
                        orderStatus = order.orderStatus
                    )
                )
            } catch (e: Exception) {
                Log.i("Order", "updateOrder: " + e.message.toString())
            }
        }
    }

    fun deleteOrder(orderUiState: OrderUiState) {
        viewModelScope.launch {
            try {
                val order = orderUiState.order.first()

                orderRepository.deleteOrder(
                    Order(
                        userID = order.userID,
                        method = order.method,
                        amount = order.amount,
                        dateTime = order.dateTime,
                        paymentStatus = order.paymentStatus,
                        orderStatus = order.orderStatus
                    )
                )
            } catch (e: Exception) {
                Log.i("Order", "deleteOrder: " + e.message.toString())
            }
        }
    }
}