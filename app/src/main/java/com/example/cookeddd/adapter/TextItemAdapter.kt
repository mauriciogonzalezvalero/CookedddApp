package com.example.cookeddd.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeddd.R

class TextItemAdapter(
    private val items: List<String>,
    private val showNumbers: Boolean = true
) : RecyclerView.Adapter<TextItemAdapter.TextItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_text, parent, false)
        return TextItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item, position + 1)
    }

    override fun getItemCount(): Int = items.size

    inner class TextItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val numberTextView: TextView = itemView.findViewById(R.id.tvNumber)
        private val textTextView: TextView = itemView.findViewById(R.id.tvText)

        fun bind(text: String, number: Int) {
            if (showNumbers) {
                numberTextView.text = "$number."
                numberTextView.visibility = View.VISIBLE
            } else {
                numberTextView.text = "•"
                numberTextView.visibility = View.VISIBLE
            }
            textTextView.text = text
        }
    }
} 