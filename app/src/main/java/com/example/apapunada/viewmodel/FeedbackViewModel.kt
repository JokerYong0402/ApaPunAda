package com.example.apapunada.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.Feedback
import com.example.apapunada.data.repository.FeedbackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class FeedbackState(
    val feedback: Feedback = Feedback(),
    val isValid: Boolean = false,
    val errorMessage: String = ""
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

    private val _feedbackState = MutableStateFlow(FeedbackState())
    val feedbackState: StateFlow<FeedbackState> = _feedbackState.asStateFlow()

    private val _feedbackListState = MutableStateFlow(FeedbackListState())
    val feedbackListState: StateFlow<FeedbackListState> = _feedbackListState.asStateFlow()


    fun loadAllFeedbacks() {
        viewModelScope.launch(Dispatchers.IO) {
            feedbackRepository.getAllFeedbacksStream()
                .map { FeedbackListState(isLoading = false, feedbackList = it) }
                .onStart { emit(FeedbackListState(isLoading = true)) }
                .catch {
                    emit(FeedbackListState(errorMessage = it.message.toString()))
                    Log.i("Feedback", "loadAllFeedbacks: " + it.message.toString())
                }
                .collect { _feedbackListState.value = it }
        }
    }

    private fun validateFeedbackInput(): Boolean {
        return with(feedbackState.value.feedback) {
            category.isNotBlank() // TODO
        }
    }

    fun updateFeedbackState(feedback: Feedback) {
        _feedbackState.value = _feedbackState.value.copy(
            feedback = feedback, isValid = validateFeedbackInput()
        )
    }

    fun saveFeedback() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateFeedbackInput()) {
                try {
                    feedbackRepository.insertFeedback(feedbackState.value.feedback)
                } catch (e: Exception) {
                    Log.i("Feedback", "saveFeedback: " + e.message.toString())
                }
            }
        }
    }

    fun updateFeedback() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateFeedbackInput()) {
                try {
                    feedbackRepository.updateFeedback(feedbackState.value.feedback)
                } catch (e: Exception) {
                    Log.i("Feedback", "updateFeedback: " + e.message.toString())
                }
            }
        }
    }

    private suspend fun deleteFeedback() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateFeedbackInput()) {
                try {
                    feedbackRepository.deleteFeedback(feedbackState.value.feedback)
                } catch (e: Exception) {
                    Log.i("Feedback", "deleteFeedback: " + e.message.toString())
                }
            }
        }
    }
}