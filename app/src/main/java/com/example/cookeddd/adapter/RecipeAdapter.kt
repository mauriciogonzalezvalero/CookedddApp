package com.example.cookeddd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeddd.R
import com.example.cookeddd.model.Recipe

class RecipeAdapter(
    private val recipes: List<Recipe>,
    private val onRecipeClick: (Recipe) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size

    inner class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.ivRecipeImage)
        private val titleTextView: TextView = itemView.findViewById(R.id.tvRecipeTitle)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.tvRecipeDescription)
        private val caloriesTextView: TextView = itemView.findViewById(R.id.tvCalories)

        fun bind(recipe: Recipe) {
            titleTextView.text = recipe.title
            descriptionTextView.text = recipe.description
            caloriesTextView.text = itemView.context.getString(R.string.calories, recipe.calories)
            imageView.setImageResource(recipe.imageResId)

            // Set click listener
            itemView.setOnClickListener {
                onRecipeClick(recipe)
            }
        }
    }
} 