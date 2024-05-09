package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.Feedback
import kotlinx.coroutines.flow.Flow

interface FeedbackRepository {

    fun getAllFeedbacksStream() : Flow<List<Feedback>>

    suspend fun insertFeedback(feedback: Feedback)

    suspend fun updateFeedback(feedback: Feedback)

    suspend fun deleteFeedback(feedback: Feedback)
}