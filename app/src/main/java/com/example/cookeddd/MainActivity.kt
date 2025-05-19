package com.example.cookeddd

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.cookeddd.fragment.FavoriteRecipesFragment
import com.example.cookeddd.fragment.RecipeListFragment
import com.example.cookeddd.fragment.ShoppingListFragment
import com.example.cookeddd.model.RecipeCategory
import com.example.cookeddd.util.PreferencesManager

class MainActivity : AppCompatActivity() {

    private lateinit var preferencesManager: PreferencesManager
    private lateinit var homeLayout: ConstraintLayout
    private lateinit var fragmentContainer: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize preferences manager
        preferencesManager = PreferencesManager(this)

        // Find views
        homeLayout = findViewById(R.id.main)
        fragmentContainer = findViewById(R.id.fragmentContainer)

        // Set up button click listeners
        setupButtons()
    }

    private fun setupButtons() {
        val weightLossButton = findViewById<Button>(R.id.btnWeightLoss)
        val weightGainButton = findViewById<Button>(R.id.btnWeightGain)
        val favoritesButton = findViewById<Button>(R.id.btnFavorites)
        val shoppingListButton = findViewById<Button>(R.id.btnShoppingList)
        val logoutButton = findViewById<Button>(R.id.btnLogout)
        val profileButton = findViewById<Button>(R.id.btnProfile)

        weightLossButton.setOnClickListener {
            // Navigate to weight loss recipes
            showFragment(RecipeListFragment.newInstance(RecipeCategory.WEIGHT_LOSS))
        }

        weightGainButton.setOnClickListener {
            // Navigate to weight gain recipes
            showFragment(RecipeListFragment.newInstance(RecipeCategory.WEIGHT_GAIN))
        }

        favoritesButton.setOnClickListener {
            // Navigate to favorite recipes
            showFragment(FavoriteRecipesFragment.newInstance())
        }
        
        shoppingListButton.setOnClickListener {
            // Navigate to shopping list
            showFragment(ShoppingListFragment.newInstance())
        }
        
        logoutButton.setOnClickListener {
            // Cerrar sesión y volver a la pantalla de login
            logout()
        }
        
        profileButton.setOnClickListener {
            // Ir a la pantalla de perfil de usuario
            val intent = Intent(this, UserProfileActivity::class.java)
            startActivity(intent)
        }
    }
    
    private fun logout() {
        // Aquí podrías limpiar datos de sesión si los tuvieras
        // Por ejemplo, borrar preferencias, tokens, etc.
        
        // Redirigir a la pantalla de login
        val intent = Intent(this, LoginActivity::class.java)
        // Limpiar la pila de actividades para que el usuario no pueda volver atrás
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showFragment(fragment: Fragment) {
        // Hide the home UI elements but keep the main container visible
        val homeElements = arrayOf(
            findViewById<View>(R.id.tvTitle),
            findViewById<View>(R.id.tvDescription),
            findViewById<View>(R.id.btnWeightLoss),
            findViewById<View>(R.id.btnWeightGain),
            findViewById<View>(R.id.btnFavorites),
            findViewById<View>(R.id.btnShoppingList),
            findViewById<View>(R.id.btnLogout),  // También ocultar el botón de logout
            findViewById<View>(R.id.btnProfile)  // También ocultar el botón de perfil
        )
        
        for (element in homeElements) {
            element.visibility = View.GONE
        }
        
        // Make the fragment container visible
        fragmentContainer.visibility = View.VISIBLE

        // Replace current fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            // If there's only one entry in the back stack, we're at the top level fragment
            // So we should show the home screen
            if (supportFragmentManager.backStackEntryCount == 1) {
                supportFragmentManager.popBackStack()
                showHomeScreen()
            } else {
                supportFragmentManager.popBackStack()
            }
        } else {
            super.onBackPressed()
        }
    }
    
    private fun showHomeScreen() {
        // Show home UI elements again
        val homeElements = arrayOf(
            findViewById<View>(R.id.tvTitle),
            findViewById<View>(R.id.tvDescription),
            findViewById<View>(R.id.btnWeightLoss),
            findViewById<View>(R.id.btnWeightGain),
            findViewById<View>(R.id.btnFavorites),
            findViewById<View>(R.id.btnShoppingList),
            findViewById<View>(R.id.btnLogout),
            findViewById<View>(R.id.btnProfile)
        )
        
        for (element in homeElements) {
            element.visibility = View.VISIBLE
        }
        
        fragmentContainer.visibility = View.GONE
    }
}