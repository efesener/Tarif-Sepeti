package com.example.tarifsepeti.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tarifsepeti.R
import kotlinx.android.synthetic.main.ingredient_row.view.*

// Tariflerin içindeki malzemeler recyclerView' ı için adapter
class IngredientAdapter(val ingredients: ArrayList<String>) : RecyclerView.Adapter<IngredientAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ingredientName = view.ingredientName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.ingredient_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = "\u2022" + ingredients.get(position).capitalize()
        holder.ingredientName.text = text
    }

    override fun getItemCount(): Int {
        return ingredients.size
    }
}

