package com.example.apapunada.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.data.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UserUiState(
    val user: Flow<User> = flowOf(User()),
    val isValid: Boolean = true
)

data class UserListState(
    val isLoading: Boolean = true,
    val userList: Flow<List<User>> = flowOf(listOf()),
    val errorMessage: String = ""
)

enum class UserStatus {
    Active,
    Disabled,
    Deleted,
}

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _userUiState = MutableStateFlow(UserUiState())
    val userUiState: StateFlow<UserUiState> = _userUiState.asStateFlow()

    private val _userListState = MutableStateFlow(UserListState())
    val userListState: StateFlow<UserListState> = _userListState.asStateFlow()

    private fun validateInput(uiState: Flow<User> = UserUiState().user): Flow<Boolean> {
        return uiState.map {
            with(it) {
                username.isNotBlank() && email.isNotBlank() && password.isNotBlank()
            }
        }
    }

    fun loadAllUser() {
        viewModelScope.launch {
            try {
                val users = userRepository.getAllUsersStream()
                _userListState.update { it.copy(isLoading = false, userList = users) }
            } catch (e: Exception) {
                _userListState.update { it.copy(isLoading = false, errorMessage = e.message.toString()) }
            }
        }
    }

    fun loadUserByUserId(id: Int) {
        viewModelScope.launch {
            val user = userRepository.getUserStream(id)
            _userUiState.update { it.copy(user = user) }
        }
    }

    fun saveUser(userUiState: UserUiState) {
        viewModelScope.launch {
            try {
                val user = userUiState.user.first()

                userRepository.insertUser(
                    User(
                        username = user.username,
                        email = user.email,
                        password = user.password,
                        phoneNo = user.phoneNo,
                        gender = user.gender,
                        dob = user.dob,
                        image = user.image,
                        point = user.point,
                        status = user.status
                    )
                )
            } catch (e: Exception) {
                Log.i("User", "saveUser: " + e.message.toString())
            }
        }
    }

    fun updateUser(userUiState: UserUiState) {
        viewModelScope.launch {
            try {
                val user = userUiState.user.first()

                userRepository.insertUser(
                    User(
                        username = user.username,
                        email = user.email,
                        password = user.password,
                        phoneNo = user.phoneNo,
                        gender = user.gender,
                        dob = user.dob,
                        image = user.image,
                        point = user.point,
                        status = user.status
                    )
                )
            } catch (e: Exception) {
                Log.i("User", "updateUser: " + e.message.toString())
            }
        }
    }

    fun deleteUser(userUiState: UserUiState) {
        viewModelScope.launch {
            try {
                val user = userUiState.user.first()

                userRepository.deleteUser(
                    User(
                        username = user.username,
                        email = user.email,
                        password = user.password,
                        phoneNo = user.phoneNo,
                        gender = user.gender,
                        dob = user.dob,
                        image = user.image,
                        point = user.point,
                        status = user.status
                    )
                )
            } catch (e: Exception) {
                Log.i("User", "deleteUser: " + e.message.toString())
            }
        }
    }
}