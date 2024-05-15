package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.data.dataclass.Waitlist
import com.example.apapunada.viewmodel.WaitlistIDState
import com.example.apapunada.viewmodel.WaitlistWithUsername
import kotlinx.coroutines.flow.Flow

@Dao
interface WaitlistDao {
    @Insert
    suspend fun insertWaitlist(waitlist: Waitlist)

    @Update
    suspend fun updateWaitlist(waitlist: Waitlist)

    @Delete
    suspend fun deleteWaitlist(waitlist: Waitlist)

    @Query("SELECT w.waitlistID, w.userID, w.datetime, w.size, w.status," +
            "u.username FROM `waitlist` w JOIN `user` u ON w.userID = u.userID")
    fun getAllWaitlists(): Flow<List<WaitlistWithUsername>>

    @Query("SELECT * FROM `waitlist` WHERE userID = :id")
    fun getWaitlistByUserId(id: Int): Flow<List<Waitlist>>

    @Query("SELECT w.waitlistID, w.userID, w.datetime, w.size, w.status," +
            "u.username FROM `waitlist` w JOIN `user` u ON w.userID = u.userID " +
            "WHERE w.status = 'Queue'")
    fun getWaitlistsByCurrentStatus(): Flow<List<WaitlistWithUsername>>

    @Query("SELECT w.waitlistID, w.userID, w.datetime, w.size, w.status," +
            "u.username FROM `waitlist` w JOIN `user` u ON w.userID = u.userID " +
            "WHERE w.status IN ('Accepted', 'Cancelled')")
    fun getWaitlistsByHistoryStatus(): Flow<List<WaitlistWithUsername>>

    @Query("SELECT w.waitlistID, w.userID, w.datetime, w.size, w.status," +
            "u.username FROM `waitlist` w JOIN `user` u ON w.userID = u.userID " +
            "WHERE w.size LIKE :size AND (w.status = :status OR w.status = :status2)")
    fun getWaitlistBySize(status: String, status2: String, size: String): Flow<List<WaitlistWithUsername>>

    @Query("SELECT w.waitlistID, w.userID, w.datetime, w.size, w.status," +
            "u.username FROM `waitlist` w JOIN `user` u ON w.userID = u.userID " +
            "WHERE u.username LIKE :party AND (w.status = :status OR w.status = :status2)")
    fun getWaitlistByParty(status: String, status2: String, party: String): Flow<List<WaitlistWithUsername>>

    @Query("SELECT w.waitlistID, w.userID, w.datetime, w.size, w.status," +
            "u.username FROM `waitlist` w JOIN `user` u ON w.userID = u.userID " +
            "WHERE w.status LIKE :status")
    fun getWaitlistByStatus(status: String): Flow<List<WaitlistWithUsername>>

    @Query("SELECT w.waitlistID, w.userID, w.datetime, w.size, w.status," +
            "u.username FROM `waitlist` w JOIN `user` u ON w.userID = u.userID " +
            "WHERE w.waitlistID > :waitlistID AND w.status LIKE 'Queue'")
    fun getInfrontWaitlist(waitlistID: Int): Flow<List<WaitlistWithUsername>>

    @Query("SELECT w.waitlistID FROM `waitlist` w JOIN `user` u ON w.userID = u.userID " +
            "WHERE u.userID = :userID AND w.status LIKE 'Queue'")
    fun getLatestWaitlistID(userID: Int): Flow<WaitlistIDState>
}