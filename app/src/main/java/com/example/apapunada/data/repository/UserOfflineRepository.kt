package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.UserDao
import com.example.apapunada.data.dataclass.User
import kotlinx.coroutines.flow.Flow

class UserOfflineRepository(private val userDao: UserDao): UserRepository {
    override fun getAllUsersStream(): Flow<List<User>> = userDao.getAllUsers()

    override fun getUserStream(id: Int): Flow<User> = userDao.getUserById(id)

    override suspend fun insertUser(user: User) = userDao.insertUsers(user)

    override suspend fun updateUser(user: User) = userDao.updateUsers(user)

    override suspend fun deleteUser(user: User) = userDao.deleteUsers(user)
}