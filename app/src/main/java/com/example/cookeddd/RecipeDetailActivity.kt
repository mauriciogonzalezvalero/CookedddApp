package com.example.cookeddd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeddd.adapter.TextItemAdapter
import com.example.cookeddd.data.RecipeRepository
import com.example.cookeddd.model.Recipe

class RecipeDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_RECIPE_ID = "extra_recipe_id"

        fun newIntent(context: Context, recipeId: Int): Intent {
            return Intent(context, RecipeDetailActivity::class.java).apply {
                putExtra(EXTRA_RECIPE_ID, recipeId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_detail)

        // Get recipe ID from intent
        val recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, -1)
        if (recipeId == -1) {
            finish()
            return
        }

        // Get recipe from repository
        val recipe = RecipeRepository.getRecipeById(recipeId)
        if (recipe == null) {
            finish()
            return
        }

        // Set up UI with recipe data
        setupUI(recipe)
    }

    private fun setupUI(recipe: Recipe) {
        // Find views
        val imageView = findViewById<ImageView>(R.id.ivRecipeImage)
        val titleTextView = findViewById<TextView>(R.id.tvRecipeTitle)
        val descriptionTextView = findViewById<TextView>(R.id.tvRecipeDescription)
        val caloriesTextView = findViewById<TextView>(R.id.tvCalories)
        val proteinTextView = findViewById<TextView>(R.id.tvProtein)
        val carbsTextView = findViewById<TextView>(R.id.tvCarbs)
        val fatTextView = findViewById<TextView>(R.id.tvFat)
        val ingredientsRecyclerView = findViewById<RecyclerView>(R.id.rvIngredients)
        val stepsRecyclerView = findViewById<RecyclerView>(R.id.rvSteps)

        // Set data
        imageView.setImageResource(recipe.imageResId)
        titleTextView.text = recipe.title
        descriptionTextView.text = recipe.description
        caloriesTextView.text = getString(R.string.calories, recipe.calories)
        proteinTextView.text = getString(R.string.protein, recipe.protein)
        carbsTextView.text = getString(R.string.carbs, recipe.carbs)
        fatTextView.text = getString(R.string.fat, recipe.fat)

        // Set up ingredients recycler view
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(this)
        ingredientsRecyclerView.adapter = TextItemAdapter(recipe.ingredients, false)

        // Set up steps recycler view
        stepsRecyclerView.layoutManager = LinearLayoutManager(this)
        stepsRecyclerView.adapter = TextItemAdapter(recipe.steps, true)
    }
} 