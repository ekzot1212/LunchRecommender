package com.example.lunchrecommender.domain.usecase

import com.example.lunchrecommender.data.model.Menu
import com.example.lunchrecommender.domain.model.Category
import com.example.lunchrecommender.domain.repository.MenuRepository
import javax.inject.Inject

class AddMenuUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(name: String, category: Category) {
        if (name.isBlank() || category == Category.ALL) return
        val menu = Menu(name = name, category = category)
        repository.addMenu(menu)
    }
} 