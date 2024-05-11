package com.example.apapunada.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.apapunada.data.dataclass.Voucher
import com.example.apapunada.data.repository.VoucherRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class VoucherState(
    val voucher: Voucher = Voucher(),
    val isValid: Boolean = false
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
    var voucherState by mutableStateOf(VoucherState())
        private set

    var voucherListState by mutableStateOf(VoucherListState())
        private set

    fun loadAllVouchers() {
        voucherRepository.getAllVouchersStream()
            .map { VoucherListState(isLoading = false, voucherList = it) }
            .onStart { emit(VoucherListState(isLoading = true)) }
            .catch {
                emit(VoucherListState(errorMessage = it.message.toString()))
                Log.i("Voucher", "loadAllVouchers: " + it.message.toString())
            }
    }

    fun loadVouchersByUserId(id: Int) {
        voucherRepository.getVouchersByUserId(id)
            .map { VoucherListState(isLoading = false, voucherList = it) }
            .onStart { emit(VoucherListState(isLoading = true)) }
            .catch {
                emit(VoucherListState(errorMessage = it.message.toString()))
                Log.i("Voucher", "loadAllVouchers: " + it.message.toString())
            }
    }

    private fun validateVoucherInput(uiState: Voucher = voucherState.voucher): Boolean {
        return with(uiState) {
            code.isNotBlank() // TODO
        }
    }

    fun updateVoucherState(voucher: Voucher) {
        voucherState = VoucherState(
            voucher = voucher, isValid = validateVoucherInput(voucher)
        )
    }

    suspend fun saveVoucher() {
        if (validateVoucherInput()) {
            try {
                voucherRepository.insertVoucher(voucherState.voucher)
            } catch (e: Exception) {
                Log.i("Voucher", "saveVoucher: " + e.message.toString())
            }
        }
    }

    suspend fun updateVoucher() {
        if (validateVoucherInput()) {
            try {
                voucherRepository.updateVoucher(voucherState.voucher)
            } catch (e: Exception) {
                Log.i("Voucher", "updateVoucher: " + e.message.toString())
            }
        }
    }

    suspend fun deleteVoucher() {
        if (validateVoucherInput()) {
            try {
                voucherRepository.deleteVoucher(voucherState.voucher)
            } catch (e: Exception) {
                Log.i("Voucher", "deleteVoucher: " + e.message.toString())
            }
        }
    }
}