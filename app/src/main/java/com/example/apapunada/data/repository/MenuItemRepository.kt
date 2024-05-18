package com.example.apapunada.data.repository

import com.example.apapunada.data.dataclass.MenuItem
import kotlinx.coroutines.flow.Flow

interface MenuItemRepository {

    fun getAllMenuItemsStream(): Flow<List<MenuItem>>

    fun getMenuItemByMenuItemIdStream(id: Int): Flow<MenuItem>

    fun getMenuMenuItemsByCuisineStream(cuisine: String): Flow<List<MenuItem>>

    fun getUsersStream(name: String) : Flow<List<MenuItem>>

    suspend fun deleteMenuItem(menuItem: MenuItem)

    suspend fun updateMenuItem(menuItem: MenuItem)

    suspend fun insertMenuItem(menuItem: MenuItem)
}