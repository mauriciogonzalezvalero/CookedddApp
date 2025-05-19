package com.example.cookeddd

import android.app.Application
import com.example.cookeddd.data.RecipeRepository

class CookedddApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize the recipe repository
        RecipeRepository.initialize(applicationContext)
    }
} 