package com.example.apapunada.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.apapunada.data.dataclass.Feedback
import com.example.apapunada.data.repository.FeedbackRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class FeedbackState(
    val feedback: Feedback = Feedback(),
    val isValid: Boolean = false
)

data class FeedbackListState(
    val isLoading: Boolean = false,
    val feedbackList: List<Feedback> = listOf(Feedback()),
    val errorMessage: String = ""
)

enum class FeedbackCategory(name: String) {
    Packaging("Packaging"),
    Menu("Menu"),
    Delivery("Delivery"),
    App("App"),
    Environment("Environment"),
    Other("Other"),
}

class FeedbackViewModel(
    private val feedbackRepository: FeedbackRepository
): ViewModel() {
    var feedbackState by mutableStateOf(FeedbackState())
        private set

    var feedbackListState by mutableStateOf(FeedbackListState())
        private set

    fun loadAllFeedbacks() {
        feedbackRepository.getAllFeedbacksStream()
            .map { FeedbackListState(isLoading = false, feedbackList = it) }
            .onStart { emit(FeedbackListState(isLoading = true)) }
            .catch {
                emit(FeedbackListState(errorMessage = it.message.toString()))
                Log.i("Feedback", "loadAllFeedbacks: " + it.message.toString())
            }
    }

    private fun validateFeedbackInput(uiState: Feedback = feedbackState.feedback): Boolean {
        return with(uiState) {
            category.isNotBlank() // TODO
        }
    }

    fun updateFeedbackState(feedback: Feedback) {
        feedbackState = FeedbackState(
            feedback = feedback, isValid = validateFeedbackInput(feedback)
        )
    }

    suspend fun saveFeedback() {
        if (validateFeedbackInput()) {
            try {
                feedbackRepository.insertFeedback(feedbackState.feedback)
            } catch (e: Exception) {
                Log.i("Feedback", "saveFeedback: " + e.message.toString())
            }
        }
    }

    suspend fun updateFeedback() {
        if (validateFeedbackInput()) {
            try {
                feedbackRepository.updateFeedback(feedbackState.feedback)
            } catch (e: Exception) {
                Log.i("Feedback", "updateFeedback: " + e.message.toString())
            }
        }
    }

    suspend fun deleteFeedback() {
        if (validateFeedbackInput()) {
            try {
                feedbackRepository.deleteFeedback(feedbackState.feedback)
            } catch (e: Exception) {
                Log.i("Feedback", "deleteFeedback: " + e.message.toString())
            }
        }
    }
}