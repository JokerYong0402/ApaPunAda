package com.example.apapunada.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

data class LoginUserState(
    val user: User = User(),
    val isExist: Boolean = false,
    val errorMessage: String = ""
)

class AuthViewModel(
    private val userRepository: UserRepository,
): ViewModel() {

    private val _userState = MutableStateFlow(LoginUserState())
    val userState: StateFlow<LoginUserState> = _userState.asStateFlow()

    private val _userListState = MutableStateFlow(UserListState())
    val userListState: StateFlow<UserListState> = _userListState.asStateFlow()

    fun loadAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getAllUsersStream()
                .map { UserListState(isLoading = false, userList = it) }
                .onStart { emit(UserListState(isLoading = true))}
                .catch {
                    emit(UserListState(errorMessage = it.message.toString()))
                    Log.i("User", "loadAllUsers: " + it.message.toString())
                }
                .collect { _userListState.value = it }
        }
    }
    fun loginUser(username: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                loadAllUsers()
                var userList: List<User>

                do {
                    userList = userListState.value.userList
                } while (userList.isEmpty())

                userList = userList.filter { it.status == "Active" }

                val loggedInUser = userList.find { it.username == username && it.password == password }
                if (loggedInUser != null) {
                    _userState.value = userState.value.copy(
                        user = loggedInUser,
                        isExist = true,
                        errorMessage = ""
                    )
                } else {
                    _userState.value = userState.value.copy(
                        isExist = false,
                        errorMessage = "Invalid username or password."
                    )
                }
            } catch (error: Exception) {
                _userState.value = userState.value.copy(
                    errorMessage = "An error occurred: ${error.message}"
                )
                Log.e("AuthViewModel", "loginUser: ${error.message}", error)
            }
        }
    }

    fun updateLoggedInUserState(user: User) {
        _userState.value = _userState.value.copy(user = user)
    }

    fun logout() {
        _userState.value = _userState.value.copy(
            user = User(),
            isExist = false,
            errorMessage = ""
        )
    }

}