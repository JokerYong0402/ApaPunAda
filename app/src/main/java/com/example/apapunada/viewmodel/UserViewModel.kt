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

data class UserState(
    val user: User = User(),
    val isValid: Boolean = false,
    val errorMessage: String = ""
)

data class UserListState(
    val isLoading: Boolean = false,
    val userList: List<User> = listOf(),
    val errorMessage: String = ""
)

enum class Gender(name: String) {
    Male("Male"),
    Female("Female"),
    Not("Prefer not to say"),
}

enum class UserStatus(name: String) {
    Active("Active"),
    Disabled("Disabled"),
    Deleted("Deleted"),
}

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _userState = MutableStateFlow(UserState())
    val userState: StateFlow<UserState> = _userState.asStateFlow()

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

    fun loadUserByUserId(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUserStream(id)
                .map { UserState(user = it, isValid = validateInput()) }
                .catch {
                    emit(UserState(errorMessage = it.message.toString()))
                    Log.i("User", "loadUserByUserId: " + it.message.toString())
                }
                .collect { _userState.value = it }
        }
    }

    private fun validateInput(): Boolean {
        return with(_userState.value.user) {
            username.isNotBlank() && password.isNotBlank()
        }
    }

    fun updateUserState(user: User) {
        _userState.value = _userState.value.copy(user = user, isValid = validateInput())
    }

    fun saveUser() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateInput()) {
                try {
                    userRepository.insertUser(_userState.value.user)
                } catch (e: Exception) {
                    Log.i("User", "saveUser: " + e.message.toString())
                }
            }
        }
    }

    fun updateUser() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateInput()) {
                try {
                    userRepository.updateUser(_userState.value.user)
                } catch (e: Exception) {
                    Log.i("User", "updateUser: " + e.message.toString())
                }
            }
        }
    }

    private fun deleteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            if (validateInput()) {
                try {
                    userRepository.deleteUser(_userState.value.user)
                } catch (e: Exception) {
                    Log.i("User", "deleteUser: " + e.message.toString())
                }
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
//                _userListState.update {
//                it.copy(isLoading = false, errorMessage = e.message.toString())
//                }
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