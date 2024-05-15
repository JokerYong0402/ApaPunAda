package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.WaitlistDao
import com.example.apapunada.data.dataclass.Waitlist
import com.example.apapunada.viewmodel.WaitlistIDState
import com.example.apapunada.viewmodel.WaitlistWithUsername
import kotlinx.coroutines.flow.Flow

class WaitlistOfflineRepository(private val waitlistDao: WaitlistDao): WaitlistRepository {

    override fun getAllWaitlistsStream(): Flow<List<WaitlistWithUsername>> = waitlistDao.getAllWaitlists()

    override fun loadInfrontWaitlists(waitlistID: Int): Flow<List<WaitlistWithUsername>> = waitlistDao.getInfrontWaitlist(waitlistID)

    override fun loadLatestWaitlistID(userID: Int): Flow<WaitlistIDState> = waitlistDao.getLatestWaitlistID(userID)

    override fun getWaitlistByUserId(id: Int): Flow<List<Waitlist>> =
        waitlistDao.getWaitlistByUserId(id)

    override fun loadWaitlistsByCurrentStatus(): Flow<List<WaitlistWithUsername>> = waitlistDao.getWaitlistsByCurrentStatus()

    override fun loadWaitlistsByHistoryStatus(): Flow<List<WaitlistWithUsername>> = waitlistDao.getWaitlistsByHistoryStatus()

    override fun loadWaitlistBySize(status: String, status2: String, size: String): Flow<List<WaitlistWithUsername>> = waitlistDao.getWaitlistBySize(status, status2, size)

    override fun loadWaitlistByParty(status: String, status2: String, party: String): Flow<List<WaitlistWithUsername>> = waitlistDao.getWaitlistByParty(status, status2, party)

    override fun loadWaitlistByStatus(status: String): Flow<List<WaitlistWithUsername>> = waitlistDao.getWaitlistByStatus(status)
    override suspend fun deleteWaitlist(waitlist: Waitlist) = waitlistDao.deleteWaitlist(waitlist)

    override suspend fun updateWaitlist(waitlist: Waitlist) = waitlistDao.updateWaitlist(waitlist)

    override suspend fun insertWaitlist(waitlist: Waitlist) = waitlistDao.insertWaitlist(waitlist)
}