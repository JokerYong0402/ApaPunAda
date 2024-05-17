package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: User)

    @Update
    suspend fun updateUsers(users: User)

    @Delete
    suspend fun deleteUsers(users: User)

    @Query("SELECT * FROM `user`")
    fun getAllUsers(): Flow<List<User>>

    @Query("SELECT * FROM `user` WHERE userID = :id")
    fun getUserById(id: Int): Flow<User>

    @Query("SELECT * FROM user WHERE username LIKE '%' || :name || '%'")
    fun getUsersByName(name: String): Flow<List<User>>
}