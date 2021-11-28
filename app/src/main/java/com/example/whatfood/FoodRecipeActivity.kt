package com.example.whatfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class FoodRecipeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_recipe)
        val foodTextView = findViewById<TextView>(R.id.foodNameInEdit)
        val foodRecipeView = findViewById<TextView>(R.id.recipeInEdit)
        val foodArray = intent.getStringArrayExtra("foodItem")
        foodTextView.text = foodArray?.get(0)
        foodRecipeView.text = foodArray?.get(2)

        // get(1) will give post id, do the database update on button click

    }
}