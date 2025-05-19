package com.example.cookeddd.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeddd.R
import com.example.cookeddd.adapter.TextItemAdapter
import com.example.cookeddd.data.RecipeRepository
import com.example.cookeddd.model.Recipe
import com.example.cookeddd.util.ShoppingListManager

class RecipeDetailFragment : Fragment() {

    private var recipeId: Int = -1
    private var recipe: Recipe? = null

    companion object {
        private const val ARG_RECIPE_ID = "recipe_id"

        fun newInstance(recipeId: Int): RecipeDetailFragment {
            val fragment = RecipeDetailFragment()
            val args = Bundle()
            args.putInt(ARG_RECIPE_ID, recipeId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recipeId = it.getInt(ARG_RECIPE_ID, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get recipe from repository
        recipe = RecipeRepository.getRecipeById(recipeId)
        recipe?.let { setupUI(view, it) }
    }

    private fun setupUI(view: View, recipe: Recipe) {
        // Find views
        val imageView = view.findViewById<ImageView>(R.id.ivRecipeImage)
        val titleTextView = view.findViewById<TextView>(R.id.tvRecipeTitle)
        val descriptionTextView = view.findViewById<TextView>(R.id.tvRecipeDescription)
        val caloriesTextView = view.findViewById<TextView>(R.id.tvCalories)
        val proteinTextView = view.findViewById<TextView>(R.id.tvProtein)
        val carbsTextView = view.findViewById<TextView>(R.id.tvCarbs)
        val fatTextView = view.findViewById<TextView>(R.id.tvFat)
        val ingredientsRecyclerView = view.findViewById<RecyclerView>(R.id.rvIngredients)
        val stepsRecyclerView = view.findViewById<RecyclerView>(R.id.rvSteps)
        val favoriteButton = view.findViewById<ImageButton>(R.id.btnFavorite)
        val addToShoppingListButton = view.findViewById<Button>(R.id.btnAddToShoppingList)

        // Set data
        imageView.setImageResource(recipe.imageResId)
        titleTextView.text = recipe.title
        descriptionTextView.text = recipe.description
        caloriesTextView.text = getString(R.string.calories, recipe.calories)
        proteinTextView.text = getString(R.string.protein, recipe.protein)
        carbsTextView.text = getString(R.string.carbs, recipe.carbs)
        fatTextView.text = getString(R.string.fat, recipe.fat)

        // Set up ingredients recycler view
        ingredientsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        ingredientsRecyclerView.adapter = TextItemAdapter(recipe.ingredients, false)

        // Set up steps recycler view
        stepsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        stepsRecyclerView.adapter = TextItemAdapter(recipe.steps, true)

        // Set up favorite button
        updateFavoriteButton(favoriteButton, recipe.id)
        favoriteButton.setOnClickListener {
            toggleFavorite(recipe.id)
            updateFavoriteButton(favoriteButton, recipe.id)
        }
        
        addToShoppingListButton.setOnClickListener {
            ShoppingListManager.addRecipeToShoppingList(requireContext(), recipe.id)
            Toast.makeText(
                requireContext(),
                "Ingredientes añadidos a la lista de compras",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateFavoriteButton(button: ImageButton, recipeId: Int) {
        val isFavorite = RecipeRepository.isFavorite(recipeId)
        button.setImageResource(
            if (isFavorite) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )
        button.contentDescription = getString(
            if (isFavorite) R.string.remove_from_favorites
            else R.string.add_to_favorites
        )
    }

    private fun toggleFavorite(recipeId: Int) {
        if (RecipeRepository.isFavorite(recipeId)) {
            RecipeRepository.removeFavorite(recipeId)
        } else {
            RecipeRepository.addFavorite(recipeId)
        }
    }
} 