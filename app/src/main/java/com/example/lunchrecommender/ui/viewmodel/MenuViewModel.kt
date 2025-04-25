package com.example.lunchrecommender.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lunchrecommender.domain.model.Category
import com.example.lunchrecommender.data.model.Menu
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MenuUiState(
    val currentMenu: String = "메뉴를 추천받아보세요!",
    val categories: List<String> = Category.values().map { it.toDisplayName() }
)

@HiltViewModel
class MenuViewModel @Inject constructor() : ViewModel() {
    private val menuList = listOf(
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

    private val _uiState = MutableStateFlow(MenuUiState())
    val uiState: StateFlow<MenuUiState> = _uiState.asStateFlow()

    init {
        _uiState.value = MenuUiState(
            categories = Category.values().map { it.toDisplayName() }
        )
    }

    fun recommendMenu(categoryName: String) {
        val category = Category.fromDisplayName(categoryName)
        val filteredMenus = when (category) {
            Category.ALL -> menuList
            else -> menuList.filter { it.category == category }
        }

        if (filteredMenus.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                currentMenu = "해당 카테고리에 메뉴가 없습니다."
            )
            return
        }

        val randomMenu = filteredMenus.random()
        _uiState.value = _uiState.value.copy(
            currentMenu = randomMenu.name
        )
    }

    fun addNewMenu(menuName: String, categoryName: String) {
        if (menuName.isBlank()) return

        val category = Category.fromDisplayName(categoryName)
        if (category != Category.ALL) {
            val newMenu = Menu(menuName, category)
            viewModelScope.launch {
                // TODO: 메뉴 저장 로직 구현
            }
        }
    }
} 