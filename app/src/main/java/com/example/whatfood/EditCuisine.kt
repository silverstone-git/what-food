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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditCuisine : AppCompatActivity() {

    // getting the database from Firebase cloud
    private val user = FirebaseAuth.getInstance().currentUser

    private var foodArray = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cuisine)

        // fetching data from database
        val db = Firebase.firestore
        db.collection("users/${user?.uid.toString()}/posts")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {

                    // ading the food into the array
                    Log.d(TAG, "${document.id} => ${document.data}, name of recipe is: ${document.data["nameOfRecipe"].toString()}")
                    foodArray.add(document.data["nameOfRecipe"].toString())

                }
                // updating the recycler View using the food array
                val foodRecyclerView = findViewById<RecyclerView>(R.id.FoodRecyclerView)
                foodRecyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
                foodRecyclerView.adapter = FoodRecViewAdapter(foodArray)
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting recipe documents.", exception)
            }


        val mySubmitButton = findViewById<Button>(R.id.submitFood)

        mySubmitButton.setOnClickListener {
            // Write new posts to the database

            // getting the text from user input and setting the value of message to send to database
            val inputFoodView : TextInputEditText = findViewById(R.id.inputFoodField)
            val inputFood = inputFoodView.text.toString()

            if (inputFood == "") {
                Snackbar.make(
                    this,
                    findViewById(R.id.inputFoodField),
                    "Please type the name of food in the 'New Food Here' Field",
                    Snackbar.LENGTH_LONG
                ).show()
            } else {

                // Now get this inputFood into the database and in the ui
                // Adding a hashmap document in posts collection
                val post = hashMapOf(
                    "nameOfRecipe" to inputFood,
                    "timeOfAddition" to System.currentTimeMillis(),
                    "recipe" to "TBD"
                )

                // Add a new post document with a generated ID
                db.collection("users/${user!!.uid}/posts")
                    .add(post)
                    .addOnSuccessListener { documentReference ->
                        Log.d(
                            TAG,
                            "post DocumentSnapshot  added with ID: ${documentReference.id}"
                        )
                        Snackbar.make(
                            this,
                            findViewById(R.id.submitFood),
                            "Your Food $inputFood has been added in post",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    .addOnFailureListener { e ->
                        Log.w(TAG, "Error adding post snapshot", e)
                    }
            }

        }

    }



}