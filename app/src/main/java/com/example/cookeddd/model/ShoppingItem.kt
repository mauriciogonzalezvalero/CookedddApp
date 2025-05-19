package com.example.cookeddd.model

data class ShoppingItem(
    val id: Int,
    val ingredient: String,
    val quantity: String,
    var isChecked: Boolean = false
)
