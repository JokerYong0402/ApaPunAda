package com.example.apapunada.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apapunada.data.dataclass.User
import com.example.apapunada.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UserUiState(

)

class UserViewModel(private val userRepository: UserRepository): ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    fun loadAllUser() {
        viewModelScope.launch {
            val users = userRepository.getAllUsersStream()
            _userList.value =
        }
    }

    fun loadUserByUserId(id: Int) {
        viewModelScope.launch {
            val users = userRepository.getAllUsersStream()
            _userList.value =
        }
    }
}