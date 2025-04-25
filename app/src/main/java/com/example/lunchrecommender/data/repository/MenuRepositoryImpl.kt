package com.example.lunchrecommender.data.repository

import com.example.lunchrecommender.data.model.Menu
import com.example.lunchrecommender.domain.model.Category

import com.example.lunchrecommender.domain.repository.MenuRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuRepositoryImpl @Inject constructor() : MenuRepository {
    private val menuList = MutableStateFlow<List<Menu>>(emptyList())

    init {
        // 초기 데이터 설정
        menuList.value = listOf(
            Menu("김치찌개", Category.KOREAN),
            Menu("된장찌개", Category.KOREAN),
            Menu("비빔밥", Category.KOREAN),
            Menu("불고기", Category.KOREAN),
            Menu("제육볶음", Category.KOREAN),
            
            Menu("짜장면", Category.CHINESE),
            Menu("짬뽕", Category.CHINESE),
            Menu("탕수육", Category.CHINESE),
            Menu("마파두부", Category.CHINESE),
            
            Menu("초밥", Category.JAPANESE),
            Menu("라멘", Category.JAPANESE),
            Menu("돈까스", Category.JAPANESE),
            Menu("우동", Category.JAPANESE),
            
            Menu("파스타", Category.WESTERN),
            Menu("피자", Category.WESTERN),
            Menu("햄버거", Category.WESTERN),
            Menu("스테이크", Category.WESTERN)
        )
    }

    override fun getAllMenus(): Flow<List<Menu>> = menuList

    override fun getMenusByCategory(category: Category): Flow<List<Menu>> {
        return menuList.map { menus ->
            menus.filter { it.category == category }
        }
    }

    override suspend fun addMenu(menu: Menu) {
        menuList.value = menuList.value + menu
    }
} 