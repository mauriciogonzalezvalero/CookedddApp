package com.example.cookeddd.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cookeddd.R
import com.example.cookeddd.adapter.ShoppingItemAdapter
import com.example.cookeddd.model.ShoppingItem
import com.example.cookeddd.util.ShoppingListManager

class ShoppingListFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ShoppingItemAdapter
    private lateinit var clearCheckedButton: Button
    private lateinit var clearAllButton: Button
    private val shoppingItems = mutableListOf<ShoppingItem>()

    companion object {
        fun newInstance(): ShoppingListFragment {
            return ShoppingListFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shopping_list, container, false)
        
        recyclerView = view.findViewById(R.id.rvShoppingList)
        clearCheckedButton = view.findViewById(R.id.btnClearChecked)
        clearAllButton = view.findViewById(R.id.btnClearAll)
        
        setupRecyclerView()
        setupButtons()
        
        return view
    }
    
    override fun onResume() {
        super.onResume()
        loadShoppingList()
    }
    
    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = ShoppingItemAdapter(shoppingItems)
        recyclerView.adapter = adapter
    }
    
    private fun setupButtons() {
        clearCheckedButton.setOnClickListener {
            adapter.clearCheckedItems()
            saveShoppingList()
        }
        
        clearAllButton.setOnClickListener {
            adapter.clearAllItems()
            saveShoppingList()
        }
    }
    
    private fun loadShoppingList() {
        shoppingItems.clear()
        shoppingItems.addAll(ShoppingListManager.getShoppingList(requireContext()))
        adapter.notifyDataSetChanged()
    }
    
    private fun saveShoppingList() {
        ShoppingListManager.saveShoppingList(requireContext(), shoppingItems)
    }
} 