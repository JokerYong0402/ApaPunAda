package com.example.apapunada.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.data.repository.UserRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

data class UserState(
    val user: User = User(),
    val isValid: Boolean = false
)

data class UserListState(
    val isLoading: Boolean = false,
    val userList: List<User> = listOf(),
    val errorMessage: String = ""
)

enum class UserStatus(name: String) {
    Active("Active"),
    Disabled("Disable"),
    Deleted("Deleted"),
}

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    var userState by mutableStateOf(UserState())
        private set

    var userListState by mutableStateOf(UserListState())
        private set

    fun loadAllUsers() {
        userRepository.getAllUsersStream()
            .map { UserListState(isLoading = false, userList = it) }
            .onStart { emit(UserListState(isLoading = true)) }
            .catch {
                emit(UserListState(errorMessage = it.message.toString()))
                Log.i("User", "loadAllUsers: " + it.message.toString())
            }
    }

    fun loadUserByUserId(id: Int) {
        userRepository.getUserStream(id)
            .map { UserState(user = it) }
            .catch { Log.i("User", "loadUserByUserId: " + it.message.toString()) }
    }

    private fun validateInput(uiState: User = userState.user): Boolean {
        return with(uiState) {
            username.isNotBlank() && password.isNotBlank()
        }
    }

    fun updateUiState(user: User) {
        userState = UserState(user = user, isValid = validateInput(user))
    }

    suspend fun saveUser() {
        if (validateInput()) {
            try {
                userRepository.insertUser(userState.user)
            } catch (e: Exception) {
                Log.i("User", "saveUser: " + e.message.toString())
            }
        }
    }

    suspend fun updateUser() {
        if (validateInput()) {
            try {
                userRepository.updateUser(userState.user)
            } catch (e: Exception) {
                Log.i("User", "updateUser: " + e.message.toString())
            }
        }
    }

    suspend fun deleteUser() {
        if (validateInput()) {
            try {
                userRepository.deleteUser(userState.user)
            } catch (e: Exception) {
                Log.i("User", "deleteUser: " + e.message.toString())
            }
        }
    }
}


//    private val _userState = MutableStateFlow(UserState())
//    val userState: StateFlow<UserUiState> = _userState.asStateFlow()
//
//    private val _userListState = MutableStateFlow(UserListState())
//    val userListState: StateFlow<UserListState> = _userListState.asStateFlow()
//
//    private fun validateInput(uiState: UserUiState = UserState()): Boolean {
//        val validation = with(uiState.user) {
//            username.isNotBlank() && email.isNotBlank() && password.isNotBlank()
//        }
//
//        if (validation) {
//            _userState.update { it.copy(isValid = true) }
//        } else {
//            _userState.update { it.copy(isValid = false) }
//        }
//
//        return uiState.isValid
//    }
//
//    fun loadAllUser() {
//        viewModelScope.launch {
//            try {
//                val users = userRepository.getAllUsersStream()
//                _userListState.update { it.copy(isLoading = false, userList = users) }
//            } catch (e: Exception) {
//                _userListState.update { it.copy(isLoading = false, errorMessage = e.message.toString()) }
//            }
//        }
//    }
//
//    fun loadUserByUserId(id: Int) {
//        viewModelScope.launch {
//            val user = userRepository.getUserStream(id)
//            _userState.update { it.copy(user = user) }
//        }
//    }
//
//    fun saveUser(userState: UserUiState) {
//        viewModelScope.launch {
//            try {
//                val user = userState.user
//
//                userRepository.insertUser(
//                    User(
//                        username = user.username,
//                        email = user.email,
//                        password = user.password,
//                        phoneNo = user.phoneNo,
//                        gender = user.gender,
//                        dob = user.dob,
//                        image = user.image,
//                        point = user.point,
//                        status = user.status
//                    )
//                )
//            } catch (e: Exception) {
//                Log.i("User", "saveUser: " + e.message.toString())
//            }
//        }
//    }
//
//    fun updateUser(userState: UserUiState) {
//        viewModelScope.launch {
//            try {
//                val user = userState.user
//
//                userRepository.insertUser(
//                    User(
//                        username = user.username,
//                        email = user.email,
//                        password = user.password,
//                        phoneNo = user.phoneNo,
//                        gender = user.gender,
//                        dob = user.dob,
//                        image = user.image,
//                        point = user.point,
//                        status = user.status
//                    )
//                )
//            } catch (e: Exception) {
//                Log.i("User", "updateUser: " + e.message.toString())
//            }
//        }
//    }
//
//    fun deleteUser(userState: UserUiState) {
//        viewModelScope.launch {
//            try {
//                val user = userState.user
//
//                userRepository.deleteUser(
//                    User(
//                        username = user.username,
//                        email = user.email,
//                        password = user.password,
//                        phoneNo = user.phoneNo,
//                        gender = user.gender,
//                        dob = user.dob,
//                        image = user.image,
//                        point = user.point,
//                        status = user.status
//                    )
//                )
//            } catch (e: Exception) {
//                Log.i("User", "deleteUser: " + e.message.toString())
//            }
//        }
//    }