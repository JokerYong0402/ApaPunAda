package com.example.apapunada.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.Voucher
import com.example.apapunada.data.repository.VoucherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class VoucherState(
    val voucher: Voucher = Voucher(),
    val isValid: Boolean = false,
    val errorMessage: String = ""
)

data class VoucherListState(
    val isLoading: Boolean = false,
    val voucherList: List<Voucher> = listOf(Voucher()),
    val errorMessage: String = ""
)

enum class VoucherStatus(name: String) {
    Active("Active"),
    Used("Used"),
    Expired("Expired")
}

class VoucherViewModel(
    private val voucherRepository: VoucherRepository
): ViewModel() {

    private val _voucherState = MutableStateFlow(VoucherState())
    val voucherState: StateFlow<VoucherState> = _voucherState.asStateFlow()

    private val _voucherListState = MutableStateFlow(VoucherListState())
    val voucherListState: StateFlow<VoucherListState> = _voucherListState.asStateFlow()

    fun loadAllVouchers() {
        viewModelScope.launch(Dispatchers.IO) {
            voucherRepository.getAllVouchersStream()
                .map { VoucherListState(isLoading = false, voucherList = it) }
                .onStart { emit(VoucherListState(isLoading = true)) }
                .catch {
                    emit(VoucherListState(errorMessage = it.message.toString()))
                    Log.i("Voucher", "loadAllVouchers: " + it.message.toString())
                }
                .collect { _voucherListState.value = it }
        }
    }

    fun loadVoucherByCode(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            voucherRepository.getVoucherByCodeStream(code)
                .map { VoucherState(voucher = it) }
                .catch {
                    emit(VoucherState(errorMessage = it.message.toString()))
                    Log.i("Voucher", "loadAllVouchers: " + it.message.toString())
                }
                .collect { _voucherState.value = it }
        }
    }

    private fun validateVoucherInput(): Boolean {
        var validation = true
        with(voucherState.value.voucher) {
            if (code.isBlank()) {
                validation = false
                _voucherState.value = _voucherState.value.copy(
                    errorMessage = "No code"
                )
            }
        }
        // TODO
        return validation
    }

    fun updateVoucherState(voucher: Voucher) {
        _voucherState.value = _voucherState.value.copy(
            voucher = voucher, isValid = validateVoucherInput()
        )
    }

    fun saveVoucher() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateVoucherInput()) {
                try {
                    voucherRepository.insertVoucher(voucherState.value.voucher)
                } catch (e: Exception) {
                    Log.i("Voucher", "saveVoucher: " + e.message.toString())
                }
            }
        }
    }

    fun updateVoucher() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateVoucherInput()) {
                try {
                    voucherRepository.updateVoucher(voucherState.value.voucher)
                } catch (e: Exception) {
                    Log.i("Voucher", "updateVoucher: " + e.message.toString())
                }
            }
        }
    }

    private suspend fun deleteVoucher() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateVoucherInput()) {
                try {
                    voucherRepository.deleteVoucher(voucherState.value.voucher)
                } catch (e: Exception) {
                    Log.i("Voucher", "deleteVoucher: " + e.message.toString())
                }
            }
        }
    }
}