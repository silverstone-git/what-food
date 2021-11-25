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
import java.lang.Math.random
import com.example.whatfood.R.id.foodText as foodText1
import com.example.whatfood.R.id.displayProfile as dp1

class MainActivity : AppCompatActivity() {

    // Will connect to database in future
    // also language conversion is to be done
    private val foodList = arrayOf(
        "Chhole Bhature",
        "Dal Parantha",
        "Red Sauce Pasta",
        "White Sauce Pasta",
        "Macaroni",
        "Bread Butter",
        "Mayo Sandwich",
        "Bread Peanut Butter",
        "Mooli Parantha",
        "Aloo Parantha",
        "Pyaz Parantha",
        "Chilla",
        "Idli",
        "Oats",
        "Dal Parantha",
        "Pav Bhaji",
        "Seviyaan",
        "Poha",
        "Chuha Bread",
        "Aloo Pakoda",
        "Pyaz Pakoda",
        "Maggi",
        "Palak Parantha",
        "Paneer Parantha",
        "Sooji Bread",
        "Vegetable Dalia",
        "Kathi Roll",
        "Chhole Bhature",
        "Chana Kulcha",
        "Upma",
        "Thepla",
        "Paneer Bhurji",
        "Palak Puri",
        "Omelette"
    )


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

            checkAndAddUser(user, view = View(this))


        }
    }


    fun genrand(view: View) {

        // The Function being called to change the main text again and again
        val floatIndex = random() * foodList.size
        val index = floatIndex.toInt()


        // The actual Changing of text
        val textView: TextView = findViewById(foodText1)
        textView.text = foodList[index]
    }


   fun goEdit(view: View)  {
        startActivity(Intent(this, EditCuisine::class.java))
   }

    private fun checkAndAddUser(user: FirebaseUser?, view: View) {

        // Checking if user is there in database:
        val db: FirebaseFirestore = Firebase.firestore
        /*
        // Execute the below code to check if the user exists, I am not going to because I have to update the user info constantly

        var userExists = false

        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "Looping through the documents: Document id in users is: ${document.id} Firebase User Id is: ${user?.uid}")
                    if (document.id == user?.uid) {
                        userExists = true
                        Log.d(TAG, "User already Exists, skipping the writing part")
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

         */

        //if ( !userExists ) {

        // Create a new user because they don't exist
        val userToBePassed = hashMapOf(
            "name" to user?.displayName,
            "email" to user?.email,
            "displayLink" to user?.photoUrl.toString(),
            "lastTimeOfLogin" to System.currentTimeMillis()
        )

        // Update the new User with a generated ID
        db.collection("users")
            // names the document as the firebase uid of the user
            .document(user?.uid.toString())
            .set(userToBePassed)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "User has been Updated")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
        //}

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