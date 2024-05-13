package com.example.apapunada.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.Waitlist
import com.example.apapunada.data.repository.WaitlistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class WaitlistState(
    val waitlist: Waitlist = Waitlist(),
    val isValid: Boolean = false,
    val errorMessage: String = ""
)

data class WaitlistListState(
    val isLoading: Boolean = false,
    val waitlistList: List<Waitlist> = listOf(Waitlist()),
    val errorMessage: String = ""
)

enum class WaitlistStatus(name: String) {
    Accepted("Accepted"),
    Queue("Queue"),
    Cancelled("Cancelled")
}

class WaitlistViewModel(
    private val waitlistRepository: WaitlistRepository
): ViewModel() {

    private val _waitlistState = MutableStateFlow(WaitlistState())
    val waitlistState: StateFlow<WaitlistState> = _waitlistState.asStateFlow()

    private val _waitlistListState = MutableStateFlow(WaitlistListState())
    val waitlistListState: StateFlow<WaitlistListState> = _waitlistListState.asStateFlow()

    fun loadAllWaitlists() {
        viewModelScope.launch(Dispatchers.IO) {
            waitlistRepository.getAllWaitlistsStream()
                .map { WaitlistListState(isLoading = false, waitlistList = it) }
                .onStart { emit(WaitlistListState(isLoading = true)) }
                .catch {
                    emit(WaitlistListState(errorMessage = it.message.toString()))
                    Log.i("Waitlist", "loadAllWaitlists: " + it.message.toString())
                }
                .collect { _waitlistListState.value = it }
        }
    }

    fun loadWaitlistsByUserId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            waitlistRepository.getWaitlistByUserId(id)
                .map { WaitlistListState(isLoading = false, waitlistList = it) }
                .onStart { emit(WaitlistListState(isLoading = true)) }
                .catch {
                    emit(WaitlistListState(errorMessage = it.message.toString()))
                    Log.i("Waitlist", "loadAllWaitlists: " + it.message.toString())
                }
                .collect { _waitlistListState.value = it }
        }
    }

    private fun validateWaitlistInput(): Boolean {
        return with(waitlistState.value.waitlist) {
            status.isNotBlank() // TODO
        }
    }

    fun updateWaitlistState(waitlist: Waitlist) {
        _waitlistState.value = _waitlistState.value.copy(
            waitlist = waitlist, isValid = validateWaitlistInput()
        )
    }

    fun saveWaitlist() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateWaitlistInput()) {
                try {
                    waitlistRepository.insertWaitlist(waitlistState.value.waitlist)
                } catch (e: Exception) {
                    Log.i("Waitlist", "saveWaitlist: " + e.message.toString())
                }
            }
        }
    }

    fun updateWaitlist() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateWaitlistInput()) {
                try {
                    waitlistRepository.updateWaitlist(waitlistState.value.waitlist)
                } catch (e: Exception) {
                    Log.i("Waitlist", "updateWaitlist: " + e.message.toString())
                }
            }
        }
    }

    private suspend fun deleteWaitlist() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateWaitlistInput()) {
                try {
                    waitlistRepository.deleteWaitlist(waitlistState.value.waitlist)
                } catch (e: Exception) {
                    Log.i("Waitlist", "deleteWaitlist: " + e.message.toString())
                }
            }
        }
    }
}