package com.example.cookeddd

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.cookeddd.model.ActivityLevel
import com.example.cookeddd.model.DietaryRestriction
import com.example.cookeddd.model.UserProfile
import com.example.cookeddd.util.UserProfileManager
import com.google.android.material.textfield.TextInputEditText

class UserProfileActivity : AppCompatActivity() {

    private lateinit var userProfileManager: UserProfileManager
    private lateinit var etName: TextInputEditText
    private lateinit var etAge: TextInputEditText
    private lateinit var etWeight: TextInputEditText
    private lateinit var etHeight: TextInputEditText
    
    // RadioButtons para nivel de actividad
    private lateinit var rbSedentary: RadioButton
    private lateinit var rbLight: RadioButton
    private lateinit var rbModerate: RadioButton
    private lateinit var rbActive: RadioButton
    private lateinit var rbVeryActive: RadioButton
    
    // CheckBoxes para restricciones dietéticas
    private lateinit var cbNone: CheckBox
    private lateinit var cbVegetarian: CheckBox
    private lateinit var cbVegan: CheckBox
    private lateinit var cbGlutenFree: CheckBox
    private lateinit var cbLactoseFree: CheckBox
    private lateinit var cbKeto: CheckBox
    private lateinit var cbLowCarb: CheckBox
    private lateinit var cbLowFat: CheckBox
    
    private lateinit var btnSaveProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user_profile)
        
        // Inicializar UserProfileManager
        userProfileManager = UserProfileManager(this)
        
        // Inicializar vistas
        initViews()
        
        // Cargar datos del perfil si existen
        loadUserProfile()
        
        // Configurar listener para el botón de guardar
        btnSaveProfile.setOnClickListener {
            saveUserProfile()
        }
        
        // Configurar el checkbox "Sin restricciones" para desactivar los demás cuando está seleccionado
        cbNone.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                cbVegetarian.isChecked = false
                cbVegan.isChecked = false
                cbGlutenFree.isChecked = false
                cbLactoseFree.isChecked = false
                cbKeto.isChecked = false
                cbLowCarb.isChecked = false
                cbLowFat.isChecked = false
                
                cbVegetarian.isEnabled = false
                cbVegan.isEnabled = false
                cbGlutenFree.isEnabled = false
                cbLactoseFree.isEnabled = false
                cbKeto.isEnabled = false
                cbLowCarb.isEnabled = false
                cbLowFat.isEnabled = false
            } else {
                cbVegetarian.isEnabled = true
                cbVegan.isEnabled = true
                cbGlutenFree.isEnabled = true
                cbLactoseFree.isEnabled = true
                cbKeto.isEnabled = true
                cbLowCarb.isEnabled = true
                cbLowFat.isEnabled = true
            }
        }
    }
    
    private fun initViews() {
        etName = findViewById(R.id.etName)
        etAge = findViewById(R.id.etAge)
        etWeight = findViewById(R.id.etWeight)
        etHeight = findViewById(R.id.etHeight)
        
        rbSedentary = findViewById(R.id.rbSedentary)
        rbLight = findViewById(R.id.rbLight)
        rbModerate = findViewById(R.id.rbModerate)
        rbActive = findViewById(R.id.rbActive)
        rbVeryActive = findViewById(R.id.rbVeryActive)
        
        cbNone = findViewById(R.id.cbNone)
        cbVegetarian = findViewById(R.id.cbVegetarian)
        cbVegan = findViewById(R.id.cbVegan)
        cbGlutenFree = findViewById(R.id.cbGlutenFree)
        cbLactoseFree = findViewById(R.id.cbLactoseFree)
        cbKeto = findViewById(R.id.cbKeto)
        cbLowCarb = findViewById(R.id.cbLowCarb)
        cbLowFat = findViewById(R.id.cbLowFat)
        
        btnSaveProfile = findViewById(R.id.btnSaveProfile)
    }
    
    private fun loadUserProfile() {
        if (userProfileManager.hasUserProfile()) {
            val userProfile = userProfileManager.getUserProfile()
            
            // Cargar datos básicos
            etName.setText(userProfile.name)
            etAge.setText(userProfile.age.toString())
            etWeight.setText(userProfile.weight.toString())
            etHeight.setText(userProfile.height.toString())
            
            // Cargar nivel de actividad
            when (userProfile.activityLevel) {
                ActivityLevel.SEDENTARY -> rbSedentary.isChecked = true
                ActivityLevel.LIGHT -> rbLight.isChecked = true
                ActivityLevel.MODERATE -> rbModerate.isChecked = true
                ActivityLevel.ACTIVE -> rbActive.isChecked = true
                ActivityLevel.VERY_ACTIVE -> rbVeryActive.isChecked = true
            }
            
            // Cargar restricciones dietéticas
            if (userProfile.dietaryRestrictions.isEmpty() || 
                userProfile.dietaryRestrictions.contains(DietaryRestriction.NONE)) {
                cbNone.isChecked = true
            } else {
                cbVegetarian.isChecked = userProfile.dietaryRestrictions.contains(DietaryRestriction.VEGETARIAN)
                cbVegan.isChecked = userProfile.dietaryRestrictions.contains(DietaryRestriction.VEGAN)
                cbGlutenFree.isChecked = userProfile.dietaryRestrictions.contains(DietaryRestriction.GLUTEN_FREE)
                cbLactoseFree.isChecked = userProfile.dietaryRestrictions.contains(DietaryRestriction.LACTOSE_FREE)
                cbKeto.isChecked = userProfile.dietaryRestrictions.contains(DietaryRestriction.KETO)
                cbLowCarb.isChecked = userProfile.dietaryRestrictions.contains(DietaryRestriction.LOW_CARB)
                cbLowFat.isChecked = userProfile.dietaryRestrictions.contains(DietaryRestriction.LOW_FAT)
            }
        } else {
            // Valores por defecto
            rbModerate.isChecked = true
            cbNone.isChecked = true
        }
    }
    
    private fun saveUserProfile() {
        // Validar datos
        val name = etName.text.toString().trim()
        val ageStr = etAge.text.toString().trim()
        val weightStr = etWeight.text.toString().trim()
        val heightStr = etHeight.text.toString().trim()
        
        if (name.isEmpty() || ageStr.isEmpty() || weightStr.isEmpty() || heightStr.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty_fields_error), Toast.LENGTH_SHORT).show()
            return
        }
        
        // Obtener nivel de actividad seleccionado
        val activityLevel = when {
            rbSedentary.isChecked -> ActivityLevel.SEDENTARY
            rbLight.isChecked -> ActivityLevel.LIGHT
            rbModerate.isChecked -> ActivityLevel.MODERATE
            rbActive.isChecked -> ActivityLevel.ACTIVE
            rbVeryActive.isChecked -> ActivityLevel.VERY_ACTIVE
            else -> ActivityLevel.MODERATE
        }
        
        // Obtener restricciones dietéticas seleccionadas
        val dietaryRestrictions = mutableListOf<DietaryRestriction>()
        if (cbNone.isChecked) {
            dietaryRestrictions.add(DietaryRestriction.NONE)
        } else {
            if (cbVegetarian.isChecked) dietaryRestrictions.add(DietaryRestriction.VEGETARIAN)
            if (cbVegan.isChecked) dietaryRestrictions.add(DietaryRestriction.VEGAN)
            if (cbGlutenFree.isChecked) dietaryRestrictions.add(DietaryRestriction.GLUTEN_FREE)
            if (cbLactoseFree.isChecked) dietaryRestrictions.add(DietaryRestriction.LACTOSE_FREE)
            if (cbKeto.isChecked) dietaryRestrictions.add(DietaryRestriction.KETO)
            if (cbLowCarb.isChecked) dietaryRestrictions.add(DietaryRestriction.LOW_CARB)
            if (cbLowFat.isChecked) dietaryRestrictions.add(DietaryRestriction.LOW_FAT)
        }
        
        // Si no se seleccionó ninguna restricción, establecer como "Sin restricciones"
        if (dietaryRestrictions.isEmpty()) {
            dietaryRestrictions.add(DietaryRestriction.NONE)
        }
        
        // Crear objeto de perfil de usuario
        val userProfile = UserProfile(
            name = name,
            age = ageStr.toInt(),
            weight = weightStr.toFloat(),
            height = heightStr.toFloat(),
            activityLevel = activityLevel,
            dietaryRestrictions = dietaryRestrictions
        )
        
        // Guardar perfil
        userProfileManager.saveUserProfile(userProfile)
        
        Toast.makeText(this, getString(R.string.profile_saved), Toast.LENGTH_SHORT).show()
        finish()
    }
} 