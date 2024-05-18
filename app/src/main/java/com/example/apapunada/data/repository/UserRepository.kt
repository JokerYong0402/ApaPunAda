package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    fun getAllUsersStream() : Flow<List<User>>

    fun getUserStream(id: Int) : Flow<User>

    fun getUsersStream(name: String) : Flow<List<User>>

    suspend fun insertUser(user: User)

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)
}