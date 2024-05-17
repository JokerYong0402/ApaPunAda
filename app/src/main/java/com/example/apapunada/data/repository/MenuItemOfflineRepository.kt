package com.example.apapunada.data.repository

import com.example.apapunada.data.dao.MenuItemDao
import com.example.apapunada.data.dataclass.MenuItem
import kotlinx.coroutines.flow.Flow

class MenuItemOfflineRepository(private val menuItemDao: MenuItemDao): MenuItemRepository {
    override fun getAllMenuItemsStream(): Flow<List<MenuItem>> = menuItemDao.getAllMenuItems()

    override fun getMenuItemByMenuItemIdStream(id: Int): Flow<MenuItem> = menuItemDao.getMenuItemByMenuItemId(id)

    override fun getMenuMenuItemsByCuisineStream(cuisine: String): Flow<List<MenuItem>> = menuItemDao.getMenuItemsByCuisine(cuisine)

    override suspend fun deleteMenuItem(menuItem: MenuItem) = menuItemDao.deleteMenuItem(menuItem)

    override suspend fun updateMenuItem(menuItem: MenuItem) = menuItemDao.updateMenuItem(menuItem)

    override suspend fun insertMenuItem(menuItem: MenuItem) = menuItemDao.insertMenuItem(menuItem)

}

