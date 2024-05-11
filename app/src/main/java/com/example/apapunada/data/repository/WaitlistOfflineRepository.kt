package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.WaitlistDao
import com.example.apapunada.data.dataclass.Waitlist
import kotlinx.coroutines.flow.Flow

class WaitlistOfflineRepository(private val waitlistDao: WaitlistDao): WaitlistRepository {

    override fun getAllWaitlistsStream(): Flow<List<Waitlist>> = waitlistDao.getAllWaitlists()

    override fun getWaitlistByUserId(id: Int): Flow<List<Waitlist>> =
        waitlistDao.getWaitlistByUserId(id)

    override suspend fun deleteWaitlist(waitlist: Waitlist) = waitlistDao.deleteWaitlist(waitlist)

    override suspend fun updateWaitlist(waitlist: Waitlist) = waitlistDao.updateWaitlist(waitlist)

    override suspend fun insertWaitlist(waitlist: Waitlist) = waitlistDao.insertWaitlist(waitlist)
}