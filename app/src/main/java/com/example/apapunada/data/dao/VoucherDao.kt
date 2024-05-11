package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.Voucher
import kotlinx.coroutines.flow.Flow

@Dao
interface VoucherDao {
    @Insert
    suspend fun insertVoucher(voucher: Voucher)

    @Update
    suspend fun updateVoucher(voucher: Voucher)

    @Delete
    suspend fun deleteVoucher(voucher: Voucher)

    @Query("SELECT * FROM `voucher`")
    fun getAllVouchers(): Flow<List<Voucher>>

    @Query("SELECT * FROM `voucher` WHERE userID = :id")
    fun getVouchersByUserId(id: Int): Flow<List<Voucher>>
}