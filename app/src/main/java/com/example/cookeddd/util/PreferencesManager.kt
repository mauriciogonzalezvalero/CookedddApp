package com.example.cookeddd.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFERENCES_NAME, Context.MODE_PRIVATE
    )
    
    companion object {
        private const val PREFERENCES_NAME = "cookeddd_preferences"
        private const val KEY_LAST_CATEGORY = "last_category"
        private const val KEY_FAVORITE_RECIPES = "favorite_recipes"
    }
    
    fun saveLastCategory(category: String) {
        sharedPreferences.edit().putString(KEY_LAST_CATEGORY, category).apply()
    }
    
    fun getLastCategory(): String? {
        return sharedPreferences.getString(KEY_LAST_CATEGORY, null)
    }
    
    fun addFavoriteRecipe(recipeId: Int) {
        val favorites = getFavoriteRecipes().toMutableSet()
        favorites.add(recipeId.toString())
        sharedPreferences.edit().putStringSet(KEY_FAVORITE_RECIPES, favorites).apply()
    }
    
    fun removeFavoriteRecipe(recipeId: Int) {
        val favorites = getFavoriteRecipes().toMutableSet()
        favorites.remove(recipeId.toString())
        sharedPreferences.edit().putStringSet(KEY_FAVORITE_RECIPES, favorites).apply()
    }
    
    fun isFavoriteRecipe(recipeId: Int): Boolean {
        return getFavoriteRecipes().contains(recipeId.toString())
    }
    
    fun getFavoriteRecipes(): Set<String> {
        return sharedPreferences.getStringSet(KEY_FAVORITE_RECIPES, emptySet()) ?: emptySet()
    }
} 