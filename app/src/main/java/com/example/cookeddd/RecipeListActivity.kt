package com.example.cookeddd

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeddd.adapter.RecipeAdapter
import com.example.cookeddd.data.RecipeRepository
import com.example.cookeddd.model.RecipeCategory
import com.example.cookeddd.model.Recipe

class RecipeListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter

    companion object {
        private const val EXTRA_CATEGORY = "extra_category"

        fun newIntent(context: Context, category: RecipeCategory): Intent {
            return Intent(context, RecipeListActivity::class.java).apply {
                putExtra(EXTRA_CATEGORY, category.name)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        // Get the category from the intent
        val categoryName = intent.getStringExtra(EXTRA_CATEGORY) ?: RecipeCategory.WEIGHT_LOSS.name
        val category = RecipeCategory.valueOf(categoryName)

        // Set the title based on category
        val titleTextView = findViewById<android.widget.TextView>(R.id.tvCategoryTitle)
        titleTextView.text = when (category) {
            RecipeCategory.WEIGHT_LOSS -> getString(R.string.weight_loss)
            RecipeCategory.WEIGHT_GAIN -> getString(R.string.weight_gain)
        }

        // Set up RecyclerView
        recyclerView = findViewById(R.id.rvRecipes)
        recyclerView.layoutManager = LinearLayoutManager(this)
        
        // Get recipes for the selected category
        val recipes = RecipeRepository.getRecipesByCategory(category)
        
        // Set up adapter with click listener
        adapter = RecipeAdapter(recipes) { recipe ->
            // Navigate to recipe detail screen
            startActivity(RecipeDetailActivity.newIntent(this, recipe.id))
        }
        
        recyclerView.adapter = adapter
    }
} 