package com.example.lunchrecommender.domain.model

enum class Category {
    ALL, KOREAN, CHINESE, JAPANESE, WESTERN;
    
    fun toDisplayName(): String = when(this) {
        ALL -> "전체"
        KOREAN -> "한식"
        CHINESE -> "중식"
        JAPANESE -> "일식"
        WESTERN -> "양식"
    }

    companion object {
        fun fromDisplayName(displayName: String): Category {
            return values().find { it.toDisplayName() == displayName } ?: ALL
        }
    }
} 