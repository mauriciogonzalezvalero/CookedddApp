package com.example.cookeddd.model

enum class RecipeCategory {
    WEIGHT_LOSS,
    WEIGHT_GAIN
}

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val imageResId: Int,
    val category: RecipeCategory,
    val ingredients: List<String>,
    val steps: List<String>,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int
) 