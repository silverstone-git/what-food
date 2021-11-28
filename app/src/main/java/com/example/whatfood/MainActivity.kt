package com.example.whatfood

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.whatfood.R.id.nameDisplay
import com.firebase.ui.auth.AuthUI
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList
import com.example.whatfood.R.id.foodText as foodText1
import com.example.whatfood.R.id.displayProfile as dp1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Getting the Views in which user info is to be filled from the layout xml
        val displayImageView: ImageView = findViewById(dp1)
        val nameDisplayView: TextView = findViewById(nameDisplay)

        // Getting the user info from Firebase, and checking if its a logged in user, and if they aren't, start the log in activity
        val user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else {
            // Here, we take the data pertaining to the user by logging the user to Firebase DB

            Glide.with(this).load(user.photoUrl)
                .listener(
                    object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            // Have to do some Progress Bar hiding here, if made in the first place
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            // Have to do some Progress Bar hiding here, if made in the first place
                            return false
                        }
                    }
                ).into(displayImageView)

            nameDisplayView.text = user.displayName

            updateTheUser(user, view = View(this))

            val feelHungry = findViewById<Button>(R.id.mainButton)
            val editCuisineButton = findViewById<Button>(R.id.whatsInTheMenu)

            // When the user presses the feel hungry button, give them a random food
            feelHungry.setOnClickListener {

                // fetching data from database
                val db = Firebase.firestore
                val foodArray = ArrayList<Food>()
                db.collection("users/${user.uid}/posts")
                    .get()
                    .addOnSuccessListener { result ->
                        for (document in result) {

                            // adding the food to the array
                            Log.d(ContentValues.TAG, "${document.id} => ${document.data}, name of recipe is: ${document.data["nameOfRecipe"].toString()}")
                            foodArray.add(Food(document.data["nameOfRecipe"].toString(), document.id, document.data["recipe"].toString()))

                            val sizeOfArray = foodArray.size
                            if ( sizeOfArray > 0 ) {

                                // Taking a random element from the array and updating the TextView with it
                                val textView: TextView = findViewById(foodText1)
                                textView.text = foodArray.random().name

                            } else {
                                Log.d(TAG, "Bro no food data was present")
                            }
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.w(ContentValues.TAG, "Error getting recipe documents.", exception)
                    }

                }

            // The Edit Cuisine Button sends the user to another activity
            editCuisineButton.setOnClickListener {
                startActivity(Intent(this, EditCuisine::class.java))
            }
        }
    }

    private fun updateTheUser(user: FirebaseUser?, view: View) {

        val db: FirebaseFirestore = Firebase.firestore

        // The User Hash Map to be sent to Database as a 'document' in the users collection
        val userToBePassed = hashMapOf(
            "name" to user?.displayName,
            "email" to user?.email,
            "displayLink" to user?.photoUrl.toString(),
            "lastTimeOfLogin" to System.currentTimeMillis()
        )

        // Update the new User with a generated ID
        db.collection("users")
            // names the document as the firebase uid of the user
            .document(user!!.uid.toString())
            .set(userToBePassed)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "User has been Updated")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.action_logout -> {
                AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        // ...
                    }


                Snackbar.make(
                    this,
                    findViewById(R.id.action_logout),
                    "You Have Been Successfully Logged Out",
                    Snackbar.LENGTH_LONG
                ).show()

                startActivity(Intent(this, LoginActivity::class.java).putExtra(Intent.EXTRA_TEXT, "Bruh you logged out"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}