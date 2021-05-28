package com.example.tarifsepeti

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recycler_list.view.*

class ListeRecyclerAdapter( val yemekListesi:ArrayList<String> , val idListesi:ArrayList<Int>): RecyclerView.Adapter<ListeRecyclerAdapter.YemekHolder>(){

    class YemekHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_list,parent,false)
        return YemekHolder(view)
    }


    override fun getItemCount(): Int {
      return yemekListesi.size
    }

    override fun onBindViewHolder(holder: YemekHolder, position: Int) {
        holder.itemView.recycler_list_text.text = yemekListesi[position]
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,TarifDetay::class.java)    //tıklayınca tarif detaya gitmek için
            intent.putExtra("Id", idListesi[position])
            holder.itemView.context.startActivity(intent)
        }
    }


}