package com.example.apapunada.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.apapunada.data.dataclass.Waitlist
import com.example.apapunada.data.repository.WaitlistRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class WaitlistState(
    val waitlist: Waitlist = Waitlist(),
    val isValid: Boolean = false
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
    var waitlistState by mutableStateOf(WaitlistState())
        private set

    var waitlistListState by mutableStateOf(WaitlistListState())
        private set

    fun loadAllWaitlists() {
        waitlistRepository.getAllWaitlistsStream()
            .map { WaitlistListState(isLoading = false, waitlistList = it) }
            .onStart { emit(WaitlistListState(isLoading = true)) }
            .catch {
                emit(WaitlistListState(errorMessage = it.message.toString()))
                Log.i("Waitlist", "loadAllWaitlists: " + it.message.toString())
            }
    }

    fun loadWaitlistsByUserId(id: Int) {
        waitlistRepository.getWaitlistByUserId(id)
            .map { WaitlistListState(isLoading = false, waitlistList = it) }
            .onStart { emit(WaitlistListState(isLoading = true)) }
            .catch {
                emit(WaitlistListState(errorMessage = it.message.toString()))
                Log.i("Waitlist", "loadAllWaitlists: " + it.message.toString())
            }
    }

    private fun validateWaitlistInput(uiState: Waitlist = waitlistState.waitlist): Boolean {
        return with(uiState) {
            status.isNotBlank()
        }
    }

    fun updateWaitlistState(waitlist: Waitlist) {
        waitlistState = WaitlistState(
            waitlist = waitlist, isValid = validateWaitlistInput(waitlist)
        )
    }

    suspend fun saveWaitlist() {
        if (validateWaitlistInput()) {
            try {
                waitlistRepository.insertWaitlist(waitlistState.waitlist)
            } catch (e: Exception) {
                Log.i("Waitlist", "saveWaitlist: " + e.message.toString())
            }
        }
    }

    suspend fun updateWaitlist() {
        if (validateWaitlistInput()) {
            try {
                waitlistRepository.updateWaitlist(waitlistState.waitlist)
            } catch (e: Exception) {
                Log.i("Waitlist", "updateWaitlist: " + e.message.toString())
            }
        }
    }

    suspend fun deleteWaitlist() {
        if (validateWaitlistInput()) {
            try {
                waitlistRepository.deleteWaitlist(waitlistState.waitlist)
            } catch (e: Exception) {
                Log.i("Waitlist", "deleteWaitlist: " + e.message.toString())
            }
        }
    }
}