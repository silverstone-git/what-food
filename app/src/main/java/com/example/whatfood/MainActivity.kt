package com.example.whatfood

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.lang.Math.random
import com.example.whatfood.R.id.foodText as foodText1

class MainActivity : AppCompatActivity() {

    // Will connect to database in future
    // also language conversion is to be done
    val foodList = arrayOf(
        "Chhole Bhature",
        "Dal Parantha",
        "Red Sauce Pasta",
        "White Sauce Pasta",
        "Macaroni" ,
        "Bread Butter" ,
        "Mayo Sandwich" ,
        "Bread Peanut Butter" ,
        "Mooli Parantha" ,
        "Aloo Parantha" ,
        "Pyaz Parantha",
        "Chilla",
        "Idli",
        "Oats" ,
        "Dal Parantha" ,
        "Pav Bhaji",
        "Seviyaan",
        "Poha",
        "Chuha Bread",
        "Aloo Pakoda" ,
        "Pyaz Pakoda",
        "Maggi" ,
        "Palak Parantha" ,
        "Paneer Parantha" ,
        "Sooji Bread" ,
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

        val user = FirebaseAuth.getInstance().currentUser
        if ( user == null ) {
            startActivity(Intent(this, loginActivity::class.java))
        } else {
            // Here, we take the data pertaining to the user by logging the user to Firebase DB
            Log.d("User Display Name: ", user.displayName!!)
        }
    }

    fun genrand(view: android.view.View) {

        // The Function being called to change the main text again and again
        val floatindex = random() * foodList.size
        val index = floatindex.toInt()


        // The actual Changing of text
        val textView : TextView = findViewById(foodText1)
        textView.text = foodList[index]
    }
}