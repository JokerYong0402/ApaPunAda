package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.Waitlist
import com.example.apapunada.viewmodel.WaitlistIDState
import com.example.apapunada.viewmodel.WaitlistWithUsername
import kotlinx.coroutines.flow.Flow

interface WaitlistRepository {

    fun getAllWaitlistsStream(): Flow<List<WaitlistWithUsername>>

    fun loadInfrontWaitlists(waitlistID: Int): Flow<List<WaitlistWithUsername>>

    fun loadQueueWaitlistID(userID: Int): Flow<Int>

    fun getWaitlistByUserId(id: Int): Flow<List<Waitlist>>

    fun loadWaitlistsByCurrentStatus(): Flow<List<WaitlistWithUsername>>

    fun loadWaitlistsByHistoryStatus(): Flow<List<WaitlistWithUsername>>

    fun loadWaitlistBySize(status: String, status2: String, size: Int): Flow<List<WaitlistWithUsername>>

    fun loadWaitlistByParty(status: String, status2: String, party: String): Flow<List<WaitlistWithUsername>>

    fun loadWaitlistByStatus(status: String): Flow<List<WaitlistWithUsername>>
    suspend fun deleteWaitlist(waitlist: Waitlist)

    suspend fun updateWaitlist(waitlist: Waitlist)

    suspend fun insertWaitlist(waitlist: Waitlist)
}