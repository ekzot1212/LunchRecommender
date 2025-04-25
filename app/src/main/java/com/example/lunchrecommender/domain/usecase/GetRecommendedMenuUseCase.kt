package com.example.lunchrecommender.domain.usecase

import com.example.lunchrecommender.domain.model.Category
import com.example.lunchrecommender.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetRecommendedMenuUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    operator fun invoke(category: Category): Flow<String> {
        return when (category) {
            Category.ALL -> repository.getAllMenus()
            else -> repository.getMenusByCategory(category)
        }.map { menus ->
            menus.randomOrNull()?.name ?: "메뉴가 없습니다"
        }
    }
} 