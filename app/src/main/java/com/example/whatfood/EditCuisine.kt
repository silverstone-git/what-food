package com.example.whatfood

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditCuisine : AppCompatActivity() {

    // getting the database from Firebase cloud
    //private val user = FirebaseAuth.getInstance().currentUser

    private var foodArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cuisine)

        // fetching data from database
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        Toast.makeText(applicationContext, "the list is : $foodArray", Toast.LENGTH_SHORT).show()
        val foodRecyclerView = findViewById<RecyclerView>(R.id.FoodRecyclerView)
        foodRecyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        foodRecyclerView.adapter = FoodRecViewAdapter(foodArray)

        val mySubmitButton = findViewById<Button>(R.id.submitFood)

        mySubmitButton.setOnClickListener(View.OnClickListener {
            fun submitMyFood(view: View) {
                // Write new data to the database

                // getting the text from user input and setting the value of message to send to database
                val inputFoodView : TextInputEditText = findViewById(R.id.inputFoodField)
                val inputFood = inputFoodView.text.toString()

                //...
                // do something with inputFood

                Snackbar.make(
                    this,
                    findViewById(R.id.submitFood),
                    "Your Food $inputFood has been added.",
                    Snackbar.LENGTH_LONG
                ).show()

            }
        })

    }



}