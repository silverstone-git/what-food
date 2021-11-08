package com.example.whatfood

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.lang.Math.random
import com.example.whatfood.R.id.foodText as foodText1

class MainActivity : AppCompatActivity() {

    // Will connect to database in future
    // also language conversion is to be done
    val foodList = arrayOf(
        "Rajmah Chawal",
        "Chhole Bhature",
        "Dal Parantha",
        "Pizza",
        "Pasta",
        "Macaroni",
        "Kofte",
        "Bhindi" ,
        "Urad Dal",
        "Moong Dal",
        "Arhar Dal",
        "Aloo Parantha",
        "Red Sauce Pasta",
        "White Sauce Pasta",
        "Macaroni" ,
        "Suji Bread" ,
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
        "Bread Pakoda" ,
        "Aloo Pakoda" ,
        "Pyaz Pakoda",
        "Maggi" ,
        "Aloo Nuggets" ,
        "Cheese Nuggets" ,
        "Aloo Smilies" ,
        "French Fries" ,
        "Palak Parantha" ,
        "Paneer Parantha" ,
        "Bread Masala" ,
        "Dalia",
        "Kathi Roll",
        "Mix Veg",
        "Chaap",
        "Aloo Matar",
        "Chhole Bhature",
        "Chana Kulcha",
        "Bhindi",
        "Kadhi Chawal",
        "Fried Rice",
        "Pulao",
        "Paneer Bhurji",
        "Paneer Basar",
        "Paneer Butter Masala",
        "Paneer Shahi",
        "Dal Makhani",
        "Rajmah",
        "Gajar Matar",
        "Chhole",
        "Lobhia",
        "Gobhi"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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