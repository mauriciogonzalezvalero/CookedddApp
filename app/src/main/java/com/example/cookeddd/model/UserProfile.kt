package com.example.cookeddd.model

data class UserProfile(
    var name: String = "",
    var age: Int = 0,
    var weight: Float = 0f,  // en kg
    var height: Float = 0f,  // en cm
    var activityLevel: ActivityLevel = ActivityLevel.MODERATE,
    var dietaryRestrictions: MutableList<DietaryRestriction> = mutableListOf()
)

enum class ActivityLevel(val description: String) {
    SEDENTARY("Sedentario"),
    LIGHT("Actividad ligera"),
    MODERATE("Actividad moderada"),
    ACTIVE("Activo"),
    VERY_ACTIVE("Muy activo")
}

enum class DietaryRestriction(val description: String) {
    NONE("Sin restricciones"),
    VEGETARIAN("Vegetariano"),
    VEGAN("Vegano"),
    GLUTEN_FREE("Sin gluten"),
    LACTOSE_FREE("Sin lactosa"),
    KETO("Keto"),
    LOW_CARB("Bajo en carbohidratos"),
    LOW_FAT("Bajo en grasas")
} 