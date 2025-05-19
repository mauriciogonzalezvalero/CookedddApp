package com.example.cookeddd.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeddd.R
import com.example.cookeddd.adapter.RecipeAdapter
import com.example.cookeddd.data.RecipeRepository

class FavoriteRecipesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set title
        val titleTextView = view.findViewById<TextView>(R.id.tvCategoryTitle)
        titleTextView.text = getString(R.string.favorite_recipes)

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.rvRecipes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        // Load favorite recipes
        loadFavoriteRecipes()
    }

    override fun onResume() {
        super.onResume()
        // Reload favorites when returning to this fragment
        loadFavoriteRecipes()
    }

    private fun loadFavoriteRecipes() {
        // Get favorite recipes from repository
        val recipes = RecipeRepository.getFavoriteRecipes()
        
        // Set up adapter with click listener
        adapter = RecipeAdapter(recipes) { recipe ->
            // Navigate to recipe detail fragment
            val detailFragment = RecipeDetailFragment.newInstance(recipe.id)
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, detailFragment)
                .addToBackStack(null)
                .commit()
        }
        
        recyclerView.adapter = adapter
    }

    companion object {
        fun newInstance(): FavoriteRecipesFragment {
            return FavoriteRecipesFragment()
        }
    }
} 