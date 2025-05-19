package com.example.cookeddd.util

import android.content.Context
import android.content.SharedPreferences
import com.example.cookeddd.data.RecipeRepository
import com.example.cookeddd.model.Recipe
import com.example.cookeddd.model.ShoppingItem
import org.json.JSONArray
import org.json.JSONObject

object ShoppingListManager {
    
    private const val PREFS_NAME = "shopping_list_prefs"
    private const val KEY_SHOPPING_ITEMS = "shopping_items"
    
    fun getShoppingList(context: Context): List<ShoppingItem> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonString = prefs.getString(KEY_SHOPPING_ITEMS, "[]") ?: "[]"
        
        return try {
            val jsonArray = JSONArray(jsonString)
            val items = mutableListOf<ShoppingItem>()
            
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                items.add(
                    ShoppingItem(
                        id = jsonObject.getInt("id"),
                        ingredient = jsonObject.getString("ingredient"),
                        quantity = jsonObject.getString("quantity"),
                        isChecked = jsonObject.getBoolean("isChecked")
                    )
                )
            }
            
            items
        } catch (e: Exception) {
            emptyList()
        }
    }
    
    fun saveShoppingList(context: Context, items: List<ShoppingItem>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonArray = JSONArray()
        
        for (item in items) {
            val jsonObject = JSONObject().apply {
                put("id", item.id)
                put("ingredient", item.ingredient)
                put("quantity", item.quantity)
                put("isChecked", item.isChecked)
            }
            jsonArray.put(jsonObject)
        }
        
        prefs.edit().putString(KEY_SHOPPING_ITEMS, jsonArray.toString()).apply()
    }
    
    fun addRecipeToShoppingList(context: Context, recipeId: Int) {
        val recipe = RecipeRepository.getRecipeById(recipeId) ?: return
        val currentItems = getShoppingList(context).toMutableList()
        
        val newItems = parseIngredientsWithQuantities(recipe)
        
        for (newItem in newItems) {
            val existingItem = currentItems.find { it.ingredient == newItem.ingredient }
            if (existingItem == null) {
                currentItems.add(newItem)
            }

        }
        
        saveShoppingList(context, currentItems)
    }
    
    private fun parseIngredientsWithQuantities(recipe: Recipe): List<ShoppingItem> {
        val items = mutableListOf<ShoppingItem>()
        var id = System.currentTimeMillis().toInt()
        
        for (ingredientText in recipe.ingredients) {
            // Attempt to parse quantity and ingredient
            // Format is usually "quantity unit ingredient" like "2 cups flour"
            val parts = ingredientText.split(" ", limit = 3)
            
            if (parts.size >= 2) {
                val quantity = parts[0]
                val unit = if (parts.size >= 3) parts[1] else ""
                val ingredient = if (parts.size >= 3) parts[2] else parts[1]
                
                val quantityText = if (unit.isNotEmpty()) "$quantity $unit" else quantity
                
                items.add(ShoppingItem(
                    id = id++,
                    ingredient = ingredient,
                    quantity = quantityText,
                    isChecked = false
                ))
            } else {
                // If we can't parse it, just add the whole text as ingredient
                items.add(ShoppingItem(
                    id = id++,
                    ingredient = ingredientText,
                    quantity = "",
                    isChecked = false
                ))
            }
        }
        
        return items
    }
} 