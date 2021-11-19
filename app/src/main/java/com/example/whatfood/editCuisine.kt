package com.example.whatfood

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.DatabaseReference
import org.json.JSONArray
import org.json.JSONObject

class editCuisine : AppCompatActivity() {

    // getting the database from Firebase cloud
    private lateinit var myRef: DatabaseReference = Firebase.database.reference

    //val foodArray = arrayListOf<String>("Dal Makhani", "Shahi Paneer")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_cuisine)

        var foodArray = {}

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val recievedDatabase = dataSnapshot.getValue<String>()
                JSONObject(recievedDatabase).getJSONArray("foodName").also { foodArray = it }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Snackbar.make(
                    applicationContext,
                    findViewById(R.id.getFoodButton),
                    "Value is: ${error.toString()}",
                    Snackbar.LENGTH_LONG
                ).show()
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
        val foodRecyclerView = findViewById<RecyclerView>(R.id.FoodRecyclerView)

        foodRecyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)

        foodRecyclerView.adapter = FoodRecViewAdapter(foodArray)

    }

    fun submitFood(view: View) {
        // Write a message to the database
        //val myRef = database.getReference("message")

        // getting the text from user input and setting the value of message to send to database
        val inputFoodView : TextInputEditText = findViewById(R.id.inputFoodField)
        val inputFood = inputFoodView.text

        // Finding the current length of the array to give the next key index
        val theJSON = JSONArray(myRef.child("foodName").toString())
        val jsonLength = theJSON.length()

        // Adding the desired key-value pair
        myRef.child("foodName").child((jsonLength+1).toString()).setValue(inputFood)

        Snackbar.make(
            this,
            findViewById(R.id.submitFood),
            "Your Food has been added.",
            Snackbar.LENGTH_LONG
        ).show()

    }

    fun getFood(view: View) {
        // Read from the database

//        val database = Firebase.database
//        val myRef = database.getReference("message")

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val recievedDatabase = dataSnapshot.getValue<String>()
                var foodArray = JSONObject(recievedDatabase).getJSONArray("foodName")
                // Toast.makeText(applicationContext, "The value in getfood is: $value", Toast.LENGTH_SHORT).show()
                //val gfoodTView : TextView = findViewById(R.id.foodDisplay)
                //gfoodTView.text = value

                val foodRecyclerView = findViewById<RecyclerView>(R.id.FoodRecyclerView)
                foodRecyclerView.adapter?.notifyItemInserted(foodArray.length()-1);
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Snackbar.make(
                    applicationContext,
                    findViewById(R.id.getFoodButton),
                    "Value is: ${error.toString()}",
                    Snackbar.LENGTH_LONG
                ).show()
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

}