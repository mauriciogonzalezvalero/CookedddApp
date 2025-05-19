package com.example.cookeddd.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cookeddd.R
import com.example.cookeddd.model.Recipe
import com.example.cookeddd.model.RecipeCategory

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 2
        private const val DATABASE_NAME = "RecipesDatabase.db"

        // Table names
        private const val TABLE_RECIPES = "recipes"
        private const val TABLE_FAVORITES = "favorites"

        // Common column names
        private const val COLUMN_ID = "id"
        
        // Recipes table columns
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_DESCRIPTION = "description"
        private const val COLUMN_IMAGE_RES_ID = "image_res_id"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_INGREDIENTS = "ingredients"
        private const val COLUMN_STEPS = "steps"
        private const val COLUMN_CALORIES = "calories"
        private const val COLUMN_PROTEIN = "protein"
        private const val COLUMN_CARBS = "carbs"
        private const val COLUMN_FAT = "fat"
        
        // Favorites table columns
        private const val COLUMN_RECIPE_ID = "recipe_id"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create recipes table
        val createRecipesTable = """
            CREATE TABLE $TABLE_RECIPES (
                $COLUMN_ID INTEGER PRIMARY KEY,
                $COLUMN_TITLE TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_IMAGE_RES_ID INTEGER,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_INGREDIENTS TEXT,
                $COLUMN_STEPS TEXT,
                $COLUMN_CALORIES INTEGER,
                $COLUMN_PROTEIN INTEGER,
                $COLUMN_CARBS INTEGER,
                $COLUMN_FAT INTEGER
            )
        """.trimIndent()
        
        // Create favorites table
        val createFavoritesTable = """
            CREATE TABLE $TABLE_FAVORITES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RECIPE_ID INTEGER,
                FOREIGN KEY($COLUMN_RECIPE_ID) REFERENCES $TABLE_RECIPES($COLUMN_ID)
            )
        """.trimIndent()
        
        db.execSQL(createRecipesTable)
        db.execSQL(createFavoritesTable)
        
        // Insert initial recipes
        insertInitialRecipes(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older tables if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_FAVORITES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RECIPES")
        
        // Create tables again
        onCreate(db)
    }
    
    // Insert initial recipes from the repository
    private fun insertInitialRecipes(db: SQLiteDatabase) {
        val weightLossRecipes = listOf(
            Recipe(
                id = 1,
                title = "Greek Yogurt Bowl",
                description = "A protein-rich breakfast that keeps you full longer",
                imageResId = R.drawable.yogurt_bowl,
                category = RecipeCategory.WEIGHT_LOSS,
                ingredients = listOf(
                    "1 cup Greek yogurt (0% fat)",
                    "1/2 cup mixed berries",
                    "1 tablespoon honey",
                    "1 tablespoon chia seeds"
                ),
                steps = listOf(
                    "Add Greek yogurt to a bowl",
                    "Top with mixed berries",
                    "Drizzle with honey",
                    "Sprinkle chia seeds on top"
                ),
                calories = 220,
                protein = 22,
                carbs = 26,
                fat = 4
            ),
            Recipe(
                id = 2,
                title = "Grilled Chicken Salad",
                description = "A light and nutritious lunch option",
                imageResId = R.drawable.chicken_salad,
                category = RecipeCategory.WEIGHT_LOSS,
                ingredients = listOf(
                    "4 oz grilled chicken breast",
                    "2 cups mixed greens",
                    "1/4 cup cherry tomatoes, halved",
                    "1/4 cucumber, sliced",
                    "1 tablespoon olive oil",
                    "1 tablespoon lemon juice",
                    "Salt and pepper to taste"
                ),
                steps = listOf(
                    "Grill chicken breast until cooked through",
                    "Combine mixed greens, tomatoes, and cucumber in a bowl",
                    "Slice chicken and add to salad",
                    "Mix olive oil, lemon juice, salt and pepper for dressing",
                    "Drizzle dressing over salad"
                ),
                calories = 280,
                protein = 32,
                carbs = 8,
                fat = 14
            ),
            Recipe(
                id = 3,
                title = "Vegetable Soup",
                description = "A filling, low-calorie dinner option",
                imageResId = R.drawable.vegetable_soup,
                category = RecipeCategory.WEIGHT_LOSS,
                ingredients = listOf(
                    "1 onion, diced",
                    "2 carrots, diced",
                    "2 celery stalks, diced",
                    "1 zucchini, diced",
                    "1 cup spinach",
                    "4 cups vegetable broth",
                    "1 teaspoon olive oil",
                    "1 teaspoon dried herbs",
                    "Salt and pepper to taste"
                ),
                steps = listOf(
                    "Heat oil in a pot and sauté onion until translucent",
                    "Add carrots and celery, cook for 5 minutes",
                    "Add zucchini and broth, bring to a boil",
                    "Reduce heat and simmer for 15 minutes",
                    "Add spinach and herbs in the last 2 minutes",
                    "Season with salt and pepper"
                ),
                calories = 150,
                protein = 5,
                carbs = 22,
                fat = 4
            ),
            Recipe(
                id = 7,
                title = "Egg White Omelette",
                description = "High protein, low calorie breakfast option",
                imageResId = R.drawable.vegetable_soup, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_LOSS,
                ingredients = listOf(
                    "4 egg whites",
                    "1/4 cup diced bell peppers",
                    "1/4 cup diced onions",
                    "1/4 cup spinach",
                    "1 tablespoon low-fat cheese",
                    "Salt and pepper to taste",
                    "Cooking spray"
                ),
                steps = listOf(
                    "Whisk egg whites in a bowl",
                    "Heat a non-stick pan with cooking spray",
                    "Sauté bell peppers and onions until soft",
                    "Add spinach and cook until wilted",
                    "Pour egg whites over vegetables",
                    "Cook until set, then fold in half",
                    "Sprinkle with cheese and serve"
                ),
                calories = 180,
                protein = 25,
                carbs = 6,
                fat = 3
            ),
            Recipe(
                id = 8,
                title = "Quinoa Bowl with Roasted Vegetables",
                description = "Nutrient-dense, filling lunch with complex carbs",
                imageResId = R.drawable.salmon_quinoa, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_LOSS,
                ingredients = listOf(
                    "1/2 cup cooked quinoa",
                    "1 cup mixed roasted vegetables (broccoli, carrots, zucchini)",
                    "1/4 avocado, sliced",
                    "2 tablespoons hummus",
                    "1 tablespoon lemon juice",
                    "Fresh herbs (parsley, cilantro)",
                    "Salt and pepper to taste"
                ),
                steps = listOf(
                    "Cook quinoa according to package instructions",
                    "Roast vegetables at 400°F for 20 minutes",
                    "Place quinoa in a bowl and top with roasted vegetables",
                    "Add sliced avocado and a dollop of hummus",
                    "Drizzle with lemon juice",
                    "Garnish with fresh herbs, salt and pepper"
                ),
                calories = 320,
                protein = 10,
                carbs = 45,
                fat = 12
            ),
            Recipe(
                id = 9,
                title = "Baked Cod with Steamed Vegetables",
                description = "Light dinner rich in lean protein",
                imageResId = R.drawable.salmon_quinoa, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_LOSS,
                ingredients = listOf(
                    "5 oz cod fillet",
                    "1 lemon, sliced",
                    "1 teaspoon olive oil",
                    "1 clove garlic, minced",
                    "1 cup broccoli florets",
                    "1 cup cauliflower florets",
                    "Fresh dill",
                    "Salt and pepper to taste"
                ),
                steps = listOf(
                    "Preheat oven to 375°F",
                    "Place cod on a sheet of foil",
                    "Top with lemon slices, garlic, and drizzle with olive oil",
                    "Season with salt, pepper, and dill",
                    "Fold foil into a packet and bake for 15-18 minutes",
                    "Steam broccoli and cauliflower until tender",
                    "Serve cod with steamed vegetables"
                ),
                calories = 240,
                protein = 30,
                carbs = 12,
                fat = 8
            ),
            Recipe(
                id = 10,
                title = "Cucumber Tomato Salad",
                description = "Refreshing side dish or light lunch",
                imageResId = R.drawable.chicken_salad, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_LOSS,
                ingredients = listOf(
                    "1 cucumber, diced",
                    "2 tomatoes, diced",
                    "1/4 red onion, thinly sliced",
                    "2 tablespoons fresh lemon juice",
                    "1 tablespoon olive oil",
                    "2 tablespoons fresh herbs (parsley, basil)",
                    "Salt and pepper to taste"
                ),
                steps = listOf(
                    "Combine cucumber, tomatoes, and red onion in a bowl",
                    "Whisk together lemon juice and olive oil",
                    "Pour dressing over vegetables",
                    "Add fresh herbs, salt, and pepper",
                    "Toss to combine and chill before serving"
                ),
                calories = 120,
                protein = 2,
                carbs = 10,
                fat = 8
            )
        )
        
        val weightGainRecipes = listOf(
            Recipe(
                id = 4,
                title = "Protein Smoothie",
                description = "High-calorie smoothie perfect for post-workout",
                imageResId = R.drawable.protein_smoothie,
                category = RecipeCategory.WEIGHT_GAIN,
                ingredients = listOf(
                    "1 banana",
                    "1 cup whole milk",
                    "2 tablespoons peanut butter",
                    "1 scoop protein powder",
                    "1 tablespoon honey",
                    "1/4 cup oats"
                ),
                steps = listOf(
                    "Add all ingredients to a blender",
                    "Blend until smooth",
                    "Pour into a glass and enjoy immediately"
                ),
                calories = 550,
                protein = 30,
                carbs = 58,
                fat = 22
            ),
            Recipe(
                id = 5,
                title = "Salmon with Quinoa",
                description = "Nutrient-dense meal rich in healthy fats and protein",
                imageResId = R.drawable.salmon_quinoa,
                category = RecipeCategory.WEIGHT_GAIN,
                ingredients = listOf(
                    "6 oz salmon fillet",
                    "1 cup cooked quinoa",
                    "1 tablespoon olive oil",
                    "1 cup roasted vegetables",
                    "1 avocado, sliced",
                    "Lemon juice",
                    "Salt and herbs to taste"
                ),
                steps = listOf(
                    "Preheat oven to 400°F (200°C)",
                    "Season salmon with salt and herbs",
                    "Bake salmon for 12-15 minutes",
                    "Cook quinoa according to package instructions",
                    "Roast vegetables with olive oil",
                    "Serve salmon over quinoa with vegetables and avocado",
                    "Drizzle with lemon juice"
                ),
                calories = 650,
                protein = 42,
                carbs = 45,
                fat = 32
            ),
            Recipe(
                id = 6,
                title = "Pasta with Turkey Meatballs",
                description = "Hearty meal with complex carbs and lean protein",
                imageResId = R.drawable.pasta_meatballs,
                category = RecipeCategory.WEIGHT_GAIN,
                ingredients = listOf(
                    "2 cups whole grain pasta",
                    "8 oz ground turkey",
                    "1 egg",
                    "1/4 cup breadcrumbs",
                    "1/4 cup grated parmesan",
                    "1 cup tomato sauce",
                    "2 tablespoons olive oil",
                    "Garlic, herbs, salt and pepper to taste"
                ),
                steps = listOf(
                    "Mix ground turkey, egg, breadcrumbs, parmesan, and seasonings",
                    "Form mixture into meatballs",
                    "Heat olive oil in a pan and cook meatballs until browned",
                    "Add tomato sauce to the pan and simmer",
                    "Cook pasta according to package instructions",
                    "Combine pasta with meatballs and sauce",
                    "Top with additional parmesan if desired"
                ),
                calories = 720,
                protein = 45,
                carbs = 75,
                fat = 28
            ),
            Recipe(
                id = 11,
                title = "Avocado Toast with Eggs",
                description = "Calorie-dense breakfast with healthy fats",
                imageResId = R.drawable.yogurt_bowl, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_GAIN,
                ingredients = listOf(
                    "2 slices whole grain bread",
                    "1 whole avocado, mashed",
                    "2 eggs",
                    "2 tablespoons olive oil",
                    "Red pepper flakes",
                    "Salt and pepper to taste"
                ),
                steps = listOf(
                    "Toast bread until golden brown",
                    "Fry eggs in olive oil to desired doneness",
                    "Spread mashed avocado on toast",
                    "Top each slice with a fried egg",
                    "Sprinkle with red pepper flakes, salt, and pepper"
                ),
                calories = 580,
                protein = 20,
                carbs = 35,
                fat = 40
            ),
            Recipe(
                id = 12,
                title = "Peanut Butter Banana Oatmeal",
                description = "High-calorie, nutrient-dense breakfast",
                imageResId = R.drawable.protein_smoothie, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_GAIN,
                ingredients = listOf(
                    "1 cup rolled oats",
                    "2 cups whole milk",
                    "1 banana, sliced",
                    "3 tablespoons peanut butter",
                    "1 tablespoon honey",
                    "1/4 cup chopped nuts",
                    "1 teaspoon cinnamon"
                ),
                steps = listOf(
                    "Cook oats with milk according to package instructions",
                    "Stir in peanut butter and honey until melted",
                    "Top with sliced banana and chopped nuts",
                    "Sprinkle with cinnamon"
                ),
                calories = 680,
                protein = 25,
                carbs = 80,
                fat = 32
            ),
            Recipe(
                id = 13,
                title = "Steak with Sweet Potato",
                description = "Protein-rich dinner with complex carbs",
                imageResId = R.drawable.pasta_meatballs, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_GAIN,
                ingredients = listOf(
                    "8 oz ribeye steak",
                    "1 large sweet potato",
                    "2 tablespoons butter",
                    "2 cloves garlic, minced",
                    "Fresh rosemary and thyme",
                    "1 tablespoon olive oil",
                    "Salt and pepper to taste"
                ),
                steps = listOf(
                    "Preheat oven to 400°F",
                    "Wash and pierce sweet potato, bake for 45-60 minutes",
                    "Season steak with salt and pepper",
                    "Heat olive oil in a cast iron pan until very hot",
                    "Cook steak 3-5 minutes per side for medium-rare",
                    "Add butter, garlic, and herbs to the pan",
                    "Baste steak with the butter mixture",
                    "Let steak rest for 5 minutes before slicing",
                    "Serve with baked sweet potato"
                ),
                calories = 780,
                protein = 50,
                carbs = 45,
                fat = 45
            ),
            Recipe(
                id = 14,
                title = "Chicken and Rice Bowl",
                description = "Balanced meal for muscle gain",
                imageResId = R.drawable.chicken_salad, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_GAIN,
                ingredients = listOf(
                    "6 oz chicken thighs, boneless",
                    "1 cup brown rice, cooked",
                    "1 tablespoon olive oil",
                    "1/2 cup black beans",
                    "1/2 avocado, sliced",
                    "1/4 cup salsa",
                    "2 tablespoons sour cream",
                    "Lime juice",
                    "Cilantro for garnish"
                ),
                steps = listOf(
                    "Season chicken thighs with salt and pepper",
                    "Cook chicken in olive oil until internal temperature reaches 165°F",
                    "Slice chicken into strips",
                    "Place cooked rice in a bowl",
                    "Top with chicken, black beans, and avocado",
                    "Add salsa and sour cream",
                    "Squeeze lime juice over the bowl",
                    "Garnish with cilantro"
                ),
                calories = 700,
                protein = 40,
                carbs = 60,
                fat = 35
            ),
            Recipe(
                id = 15,
                title = "Mediterranean Tuna Wrap",
                description = "Portable, protein-rich lunch",
                imageResId = R.drawable.vegetable_soup, // Reusing existing drawable
                category = RecipeCategory.WEIGHT_GAIN,
                ingredients = listOf(
                    "1 large whole wheat wrap",
                    "5 oz canned tuna in olive oil",
                    "2 tablespoons hummus",
                    "1/4 cup feta cheese",
                    "Handful of spinach leaves",
                    "5 cherry tomatoes, halved",
                    "5 kalamata olives, sliced",
                    "1 tablespoon olive oil",
                    "1 teaspoon lemon juice"
                ),
                steps = listOf(
                    "Spread hummus on the wrap",
                    "Mix tuna with olive oil and lemon juice",
                    "Layer tuna mixture, spinach, tomatoes, olives, and feta cheese on the wrap",
                    "Roll up tightly and slice in half"
                ),
                calories = 550,
                protein = 35,
                carbs = 40,
                fat = 28
            )
        )
        
        // Insert all recipes
        val allRecipes = weightLossRecipes + weightGainRecipes
        for (recipe in allRecipes) {
            insertRecipe(db, recipe)
        }
    }
    
    // Insert a recipe into the database
    private fun insertRecipe(db: SQLiteDatabase, recipe: Recipe) {
        val values = ContentValues().apply {
            put(COLUMN_ID, recipe.id)
            put(COLUMN_TITLE, recipe.title)
            put(COLUMN_DESCRIPTION, recipe.description)
            put(COLUMN_IMAGE_RES_ID, recipe.imageResId)
            put(COLUMN_CATEGORY, recipe.category.name)
            put(COLUMN_INGREDIENTS, recipe.ingredients.joinToString("|"))
            put(COLUMN_STEPS, recipe.steps.joinToString("|"))
            put(COLUMN_CALORIES, recipe.calories)
            put(COLUMN_PROTEIN, recipe.protein)
            put(COLUMN_CARBS, recipe.carbs)
            put(COLUMN_FAT, recipe.fat)
        }
        
        db.insert(TABLE_RECIPES, null, values)
    }
    
    // Get all recipes from the database
    fun getAllRecipes(): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_RECIPES"
        val cursor = db.rawQuery(query, null)
        
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID))
                val categoryStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
                val ingredientsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS))
                val stepsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STEPS))
                val calories = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CALORIES))
                val protein = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROTEIN))
                val carbs = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CARBS))
                val fat = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT))
                
                val category = RecipeCategory.valueOf(categoryStr)
                val ingredients = ingredientsStr.split("|")
                val steps = stepsStr.split("|")
                
                val recipe = Recipe(
                    id = id,
                    title = title,
                    description = description,
                    imageResId = imageResId,
                    category = category,
                    ingredients = ingredients,
                    steps = steps,
                    calories = calories,
                    protein = protein,
                    carbs = carbs,
                    fat = fat
                )
                
                recipes.add(recipe)
            } while (cursor.moveToNext())
        }
        
        cursor.close()
        return recipes
    }
    
    // Get recipes by category
    fun getRecipesByCategory(category: RecipeCategory): List<Recipe> {
        val recipes = mutableListOf<Recipe>()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_RECIPES WHERE $COLUMN_CATEGORY = ?"
        val cursor = db.rawQuery(query, arrayOf(category.name))
        
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                val imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID))
                val ingredientsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS))
                val stepsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STEPS))
                val calories = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CALORIES))
                val protein = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROTEIN))
                val carbs = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CARBS))
                val fat = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT))
                
                val ingredients = ingredientsStr.split("|")
                val steps = stepsStr.split("|")
                
                val recipe = Recipe(
                    id = id,
                    title = title,
                    description = description,
                    imageResId = imageResId,
                    category = category,
                    ingredients = ingredients,
                    steps = steps,
                    calories = calories,
                    protein = protein,
                    carbs = carbs,
                    fat = fat
                )
                
                recipes.add(recipe)
            } while (cursor.moveToNext())
        }
        
        cursor.close()
        return recipes
    }
    
    // Get a recipe by ID
    fun getRecipeById(id: Int): Recipe? {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_RECIPES WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(id.toString()))
        
        var recipe: Recipe? = null
        
        if (cursor.moveToFirst()) {
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
            val imageResId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IMAGE_RES_ID))
            val categoryStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY))
            val ingredientsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS))
            val stepsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STEPS))
            val calories = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CALORIES))
            val protein = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PROTEIN))
            val carbs = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CARBS))
            val fat = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_FAT))
            
            val category = RecipeCategory.valueOf(categoryStr)
            val ingredients = ingredientsStr.split("|")
            val steps = stepsStr.split("|")
            
            recipe = Recipe(
                id = id,
                title = title,
                description = description,
                imageResId = imageResId,
                category = category,
                ingredients = ingredients,
                steps = steps,
                calories = calories,
                protein = protein,
                carbs = carbs,
                fat = fat
            )
        }
        
        cursor.close()
        return recipe
    }
    
    // Add a recipe to favorites
    fun addFavorite(recipeId: Int): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_RECIPE_ID, recipeId)
        }
        
        return db.insert(TABLE_FAVORITES, null, values)
    }
    
    // Remove a recipe from favorites
    fun removeFavorite(recipeId: Int): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_FAVORITES, "$COLUMN_RECIPE_ID = ?", arrayOf(recipeId.toString()))
    }
    
    // Check if a recipe is in favorites
    fun isFavorite(recipeId: Int): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_FAVORITES WHERE $COLUMN_RECIPE_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(recipeId.toString()))
        val isFavorite = cursor.count > 0
        cursor.close()
        return isFavorite
    }
    
    // Get all favorite recipes
    fun getFavoriteRecipes(): List<Recipe> {
        val favoriteRecipeIds = mutableListOf<Int>()
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_RECIPE_ID FROM $TABLE_FAVORITES"
        val cursor = db.rawQuery(query, null)
        
        if (cursor.moveToFirst()) {
            do {
                val recipeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_ID))
                favoriteRecipeIds.add(recipeId)
            } while (cursor.moveToNext())
        }
        
        cursor.close()
        
        // Get all recipes with IDs in favoriteRecipeIds
        val favoriteRecipes = mutableListOf<Recipe>()
        for (id in favoriteRecipeIds) {
            val recipe = getRecipeById(id)
            if (recipe != null) {
                favoriteRecipes.add(recipe)
            }
        }
        
        return favoriteRecipes
    }
} 