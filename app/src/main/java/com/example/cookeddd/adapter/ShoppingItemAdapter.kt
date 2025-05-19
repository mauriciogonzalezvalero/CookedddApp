package com.example.cookeddd.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeddd.R
import com.example.cookeddd.model.ShoppingItem

class ShoppingItemAdapter(
    private val items: MutableList<ShoppingItem>
) : RecyclerView.Adapter<ShoppingItemAdapter.ShoppingItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_shopping, parent, false)
        return ShoppingItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    fun clearCheckedItems() {
        val iterator = items.iterator()
        var removed = false
        while (iterator.hasNext()) {
            if (iterator.next().isChecked) {
                iterator.remove()
                removed = true
            }
        }
        if (removed) {
            notifyDataSetChanged()
        }
    }

    fun clearAllItems() {
        items.clear()
        notifyDataSetChanged()
    }

    inner class ShoppingItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ingredientTextView: TextView = itemView.findViewById(R.id.tvIngredient)
        private val quantityTextView: TextView = itemView.findViewById(R.id.tvQuantity)
        private val checkBox: CheckBox = itemView.findViewById(R.id.checkBox)

        fun bind(item: ShoppingItem) {
            ingredientTextView.text = item.ingredient
            quantityTextView.text = item.quantity
            checkBox.isChecked = item.isChecked
            
            updateStrikeThrough(item.isChecked)
            
            checkBox.setOnClickListener {
                item.isChecked = checkBox.isChecked
                updateStrikeThrough(item.isChecked)
            }
        }
        
        private fun updateStrikeThrough(isChecked: Boolean) {
            if (isChecked) {
                ingredientTextView.paintFlags = ingredientTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                quantityTextView.paintFlags = quantityTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                ingredientTextView.paintFlags = ingredientTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                quantityTextView.paintFlags = quantityTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }
} 