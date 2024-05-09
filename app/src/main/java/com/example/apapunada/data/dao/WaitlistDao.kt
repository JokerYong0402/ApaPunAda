package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.Waitlist
import kotlinx.coroutines.flow.Flow

@Dao
interface WaitlistDao {
    @Insert
    suspend fun insertWaitlist(waitlist: Waitlist)

    @Update
    suspend fun updateWaitlist(waitlist: Waitlist)

    @Delete
    suspend fun deleteWaitlist(waitlist: Waitlist)

    @Query("SELECT * FROM `waitlist`")
    fun getAllWaitlists(): Flow<List<Waitlist>>
}