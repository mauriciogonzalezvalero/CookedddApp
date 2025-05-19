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
import com.example.cookeddd.model.Recipe
import com.example.cookeddd.model.RecipeCategory

class RecipeListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecipeAdapter
    private var category: RecipeCategory = RecipeCategory.WEIGHT_LOSS

    companion object {
        private const val ARG_CATEGORY = "category"

        fun newInstance(category: RecipeCategory): RecipeListFragment {
            val fragment = RecipeListFragment()
            val args = Bundle()
            args.putString(ARG_CATEGORY, category.name)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val categoryName = it.getString(ARG_CATEGORY) ?: RecipeCategory.WEIGHT_LOSS.name
            category = RecipeCategory.valueOf(categoryName)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set the title based on category
        val titleTextView = view.findViewById<TextView>(R.id.tvCategoryTitle)
        titleTextView.text = when (category) {
            RecipeCategory.WEIGHT_LOSS -> getString(R.string.weight_loss)
            RecipeCategory.WEIGHT_GAIN -> getString(R.string.weight_gain)
        }

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.rvRecipes)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        // Get recipes for the selected category
        val recipes = RecipeRepository.getRecipesByCategory(category)
        
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
} 