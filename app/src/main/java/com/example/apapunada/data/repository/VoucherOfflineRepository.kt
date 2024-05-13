package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.VoucherDao
import com.example.apapunada.data.dataclass.Voucher
import kotlinx.coroutines.flow.Flow

class VoucherOfflineRepository(private val voucherDao: VoucherDao): VoucherRepository {

    override fun getAllVouchersStream(): Flow<List<Voucher>> = voucherDao.getAllVouchers()

    override fun getVoucherByCodeStream(code: String): Flow<Voucher> =
        voucherDao.getVoucherByCode(code)

    override suspend fun deleteVoucher(voucher: Voucher) = voucherDao.deleteVoucher(voucher)

    override suspend fun updateVoucher(voucher: Voucher) = voucherDao.updateVoucher(voucher)

    override suspend fun insertVoucher(voucher: Voucher) = voucherDao.insertVoucher(voucher)
}