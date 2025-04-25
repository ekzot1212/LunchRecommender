package com.example.lunchrecommender.domain.repository

import com.example.lunchrecommender.domain.model.Category
import com.example.lunchrecommender.data.model.Menu
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    fun getAllMenus(): Flow<List<Menu>>
    fun getMenusByCategory(category: Category): Flow<List<Menu>>
    suspend fun addMenu(menu: Menu)
} 