package com.example.tarifsepeti.api

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tarifsepeti.R
import kotlinx.android.synthetic.main.recipe_row.view.*

// Tarifler recyclerView için adapter
class RecipesAdapter(val recipes: ArrayList<RecipeModel>) : RecyclerView.Adapter<RecipesAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recipe_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.recipeName.text = recipes.get(position).isim
        holder.itemView.recipeImage.setImageBitmap(recipes.get(position).image)

        val childLayoutManager = LinearLayoutManager(holder.itemView.ingredientRecyclerView.context)

        holder.itemView.ingredientRecyclerView.apply {
            layoutManager = childLayoutManager
            adapter = IngredientAdapter(recipes.get(position).malzemeler)
            setRecycledViewPool(viewPool)
        }
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

}