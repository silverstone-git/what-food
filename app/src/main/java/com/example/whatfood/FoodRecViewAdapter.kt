package com.example.whatfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodRecViewAdapter(val recyclerViewElements : ArrayList<Food>, private val listener: FoodItemClicked) : RecyclerView.Adapter<FoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_layout, parent, false)
        val foodViewHolder = FoodViewHolder(view)
        view.setOnClickListener{
            listener.onClicked(recyclerViewElements[foodViewHolder.absoluteAdapterPosition])
        }

        return foodViewHolder
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentElement = recyclerViewElements[position]
        holder.elementTextView.text = currentElement.name
        holder.elementRecipeView.text = currentElement.recipe
    }

    override fun getItemCount(): Int  = this.recyclerViewElements.size
}

class FoodViewHolder(elementView: View) : RecyclerView.ViewHolder(elementView) {
    val elementTextView : TextView = elementView.findViewById(R.id.ElementTextView)
    val elementRecipeView: TextView = elementView.findViewById(R.id.recipeTextView)
}

interface FoodItemClicked {
    fun onClicked(currentItem: Food)
}