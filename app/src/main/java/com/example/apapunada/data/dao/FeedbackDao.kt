package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.Feedback
import kotlinx.coroutines.flow.Flow

@Dao
interface FeedbackDao {
    @Insert
    suspend fun insertFeedback(feedback: Feedback)

    @Update
    suspend fun updateFeedback(feedback: Feedback)

    @Delete
    suspend fun deleteFeedback(feedback: Feedback)

    @Query("SELECT * FROM `feedback`")
    fun getAllFeedback(): Flow<List<Feedback>>
}