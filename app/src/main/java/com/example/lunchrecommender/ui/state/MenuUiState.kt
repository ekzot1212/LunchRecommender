package com.example.lunchrecommender.ui.state

data class MenuUiState(
    val currentMenu: String = "여기에 추천 메뉴가 표시됩니다",
    val categories: List<String> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) 