package com.example.cookeddd.data

import android.content.Context
import com.example.cookeddd.model.Recipe
import com.example.cookeddd.model.RecipeCategory

object RecipeRepository {
    
    private lateinit var dbHelper: DatabaseHelper
    
    fun initialize(context: Context) {
        dbHelper = DatabaseHelper(context)
    }
    
    fun getAllRecipes(): List<Recipe> {
        return dbHelper.getAllRecipes()
    }
    
    fun getRecipesByCategory(category: RecipeCategory): List<Recipe> {
        return dbHelper.getRecipesByCategory(category)
    }
    
    fun getRecipeById(id: Int): Recipe? {
        return dbHelper.getRecipeById(id)
    }
    
    fun addFavorite(recipeId: Int): Boolean {
        return dbHelper.addFavorite(recipeId) > 0
    }
    
    fun removeFavorite(recipeId: Int): Boolean {
        return dbHelper.removeFavorite(recipeId) > 0
    }
    
    fun isFavorite(recipeId: Int): Boolean {
        return dbHelper.isFavorite(recipeId)
    }
    
    fun getFavoriteRecipes(): List<Recipe> {
        return dbHelper.getFavoriteRecipes()
    }
} 