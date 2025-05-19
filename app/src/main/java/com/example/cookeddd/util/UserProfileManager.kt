package com.example.cookeddd.util

import android.content.Context
import android.content.SharedPreferences
import com.example.cookeddd.model.ActivityLevel
import com.example.cookeddd.model.DietaryRestriction
import com.example.cookeddd.model.UserProfile
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserProfileManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME, Context.MODE_PRIVATE
    )
    private val gson = Gson()

    companion object {
        private const val PREFS_NAME = "user_profile_prefs"
        private const val KEY_USER_PROFILE = "user_profile"
        private const val KEY_DIETARY_RESTRICTIONS = "dietary_restrictions"
    }

    fun saveUserProfile(userProfile: UserProfile) {
        val editor = sharedPreferences.edit()
        
        // Guardar datos básicos
        editor.putString("name", userProfile.name)
        editor.putInt("age", userProfile.age)
        editor.putFloat("weight", userProfile.weight)
        editor.putFloat("height", userProfile.height)
        editor.putString("activity_level", userProfile.activityLevel.name)
        
        // Guardar restricciones dietéticas como lista JSON
        val restrictionsJson = gson.toJson(userProfile.dietaryRestrictions.map { it.name })
        editor.putString(KEY_DIETARY_RESTRICTIONS, restrictionsJson)
        
        editor.apply()
    }

    fun getUserProfile(): UserProfile {
        val name = sharedPreferences.getString("name", "") ?: ""
        val age = sharedPreferences.getInt("age", 0)
        val weight = sharedPreferences.getFloat("weight", 0f)
        val height = sharedPreferences.getFloat("height", 0f)
        
        // Obtener nivel de actividad
        val activityLevelStr = sharedPreferences.getString("activity_level", ActivityLevel.MODERATE.name)
        val activityLevel = try {
            ActivityLevel.valueOf(activityLevelStr ?: ActivityLevel.MODERATE.name)
        } catch (e: Exception) {
            ActivityLevel.MODERATE
        }
        
        // Obtener restricciones dietéticas
        val restrictionsJson = sharedPreferences.getString(KEY_DIETARY_RESTRICTIONS, "[]")
        val restrictionsType = object : TypeToken<List<String>>() {}.type
        val restrictionsNames = gson.fromJson<List<String>>(restrictionsJson, restrictionsType) ?: listOf()
        
        val restrictions = restrictionsNames.mapNotNull { name ->
            try {
                DietaryRestriction.valueOf(name)
            } catch (e: Exception) {
                null
            }
        }.toMutableList()
        
        return UserProfile(name, age, weight, height, activityLevel, restrictions)
    }

    fun hasUserProfile(): Boolean {
        return sharedPreferences.contains("name") && sharedPreferences.getString("name", "")?.isNotEmpty() == true
    }

    fun clearUserProfile() {
        sharedPreferences.edit().clear().apply()
    }
} 