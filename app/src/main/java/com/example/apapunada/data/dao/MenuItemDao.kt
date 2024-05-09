package com.example.apapunada.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.apapunada.data.dataclass.MenuItem
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {
    @Insert
    suspend fun insertMenuItem(menuItem: MenuItem)

    @Update
    suspend fun updateMenuItem(menuItem: MenuItem)

    @Delete
    suspend fun deleteMenuItem(menuItem: MenuItem)

    @Query("SELECT * FROM `menuItem`")
    fun getAllMenuItems(): Flow<List<MenuItem>>

    @Query("SELECT * FROM `menuItem` WHERE menuItemID = :id")
    fun getMenuItemByMenuItemId(id: Int): Flow<MenuItem>
}