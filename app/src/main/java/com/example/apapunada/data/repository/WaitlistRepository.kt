package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.Waitlist
import kotlinx.coroutines.flow.Flow

interface WaitlistRepository {

    fun getAllWaitlistsStream(): Flow<List<Waitlist>>

    suspend fun deleteWaitlist(waitlist: Waitlist)

    suspend fun updateWaitlist(waitlist: Waitlist)

    suspend fun insertWaitlist(waitlist: Waitlist)
}