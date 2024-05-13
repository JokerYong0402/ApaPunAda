package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.Voucher
import kotlinx.coroutines.flow.Flow

interface VoucherRepository {

    fun getAllVouchersStream(): Flow<List<Voucher>>

    fun getVoucherByCodeStream(code: String): Flow<Voucher>

    suspend fun deleteVoucher(voucher: Voucher)

    suspend fun updateVoucher(voucher: Voucher)

    suspend fun insertVoucher(voucher: Voucher)
}