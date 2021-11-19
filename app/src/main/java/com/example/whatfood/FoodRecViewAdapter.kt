package com.example.whatfood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodRecViewAdapter(val recViewElements : ArrayList<String>) : RecyclerView.Adapter<FoodViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.element_layout, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val currentElement = recViewElements[position]
        holder.elementTextView.text = currentElement
    }

    override fun getItemCount(): Int  = this.recViewElements.size
}

class FoodViewHolder(elementView: View) : RecyclerView.ViewHolder(elementView) {
    val elementTextView : TextView = elementView.findViewById(R.id.ElementTextView)
}