package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.FeedbackDao
import com.example.apapunada.data.dataclass.Feedback
import kotlinx.coroutines.flow.Flow

class FeedbackOfflineRepository(private val feedbackDao: FeedbackDao): FeedbackRepository {
    override fun getAllFeedbacksStream() : Flow<List<Feedback>> = feedbackDao.getAllFeedback()

    override suspend fun insertFeedback(feedback: Feedback) = feedbackDao.insertFeedback(feedback)

    override suspend fun updateFeedback(feedback: Feedback) { feedbackDao.updateFeedback(feedback) }

    override suspend fun deleteFeedback(feedback: Feedback) = feedbackDao.deleteFeedback(feedback)
}