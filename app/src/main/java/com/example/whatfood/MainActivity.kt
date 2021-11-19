package com.example.whatfood

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
import java.lang.Math.random
import com.example.whatfood.R.id.foodText as foodText1
import com.example.whatfood.R.id.displayProfile as dp1

class MainActivity : AppCompatActivity() {

    // Will connect to database in future
    // also language conversion is to be done
    val foodList = arrayOf(
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
        val user = FirebaseAuth.getInstance().currentUser

        if (user == null) {
            startActivity(Intent(this, loginActivity::class.java))
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
        startActivity(Intent(this, editCuisine::class.java))
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

                startActivity(Intent(this, loginActivity::class.java).putExtra(Intent.EXTRA_TEXT, "Bruh you logged out"))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



}