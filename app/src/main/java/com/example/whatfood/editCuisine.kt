package com.example.whatfood

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class editCuisine : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cuisine)
    }

    fun submitFood(view: View) {
        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        // getting the text from user input and setting the value of message to send to database
        val inputFoodView : TextInputEditText = findViewById(R.id.inputFoodField)
        val inputFood = inputFoodView.text

        myRef.setValue(inputFood)

        Snackbar.make(
            this,
            findViewById(R.id.submitFood),
            "Your Food has been added.",
            Snackbar.LENGTH_LONG
        ).show()

    }

    fun getFood(view: View) {
        // Read from the database

        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val value = dataSnapshot.getValue<String>()
                Log.d(TAG, "Value is: $value")
                val gfoodTView : TextView = findViewById(R.id.foodDisplay)
                gfoodTView.text = value
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Snackbar.make(
                    applicationContext,
                    findViewById(R.id.foodDisplay),
                    "Value is: ${error.toString()}",
                    Snackbar.LENGTH_LONG
                ).show()
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

}