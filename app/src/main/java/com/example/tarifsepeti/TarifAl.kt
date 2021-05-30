package com.example.tarifsepeti

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.JsonArrayRequest
import com.example.tarifsepeti.api.MySingleton
import com.example.tarifsepeti.api.RecipeModel
import com.example.tarifsepeti.api.RecipesAdapter
import kotlinx.android.synthetic.main.activity_tarif_al.*
import org.json.JSONObject

class TarifAl : AppCompatActivity() {
    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim
    var  kullaniciAdi = ""
    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim

    val recipes = ArrayList<RecipeModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarif_al)

        // @@@@@@ Bir önceki sayfadan gelen kullaniciAdi bilgisini alıyoruz
        val intent = intent
        kullaniciAdi = intent.getStringExtra("kullaniciAdi").toString()


        // API ayarları
        val apiKey = "2823c0689b3646e0a135afb43b93a76d"
        var url =
            "https://api.spoonacular.com/recipes/findByIngredients?apiKey=${apiKey}&ingredients="

        searchButton.setOnClickListener {

            val parameter = inputSearch.text.toString()
            getRecipes(url+parameter)
        }

    }

    // @@@@@@ MENU ILE İLGİLİ FONKSİYONLAR
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.getItem(2)?.setEnabled(false) // Menüdeki bir itemi deaktive etme
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var selectedOpiton = ""

        when(item.itemId){
            R.id.tarifListele -> selectedOpiton = "tarifListele"
            R.id.tarifEkle -> selectedOpiton = "tarifEkle"
            R.id.tarifAl -> selectedOpiton = "tarifAl"
        }

        // @@@@@@ SAYFA GEÇİŞLERİ
        if(selectedOpiton == "tarifListele"){
            val intent = Intent(applicationContext,TarifListele::class.java)
            intent.putExtra("kullaniciAdi", kullaniciAdi)
            startActivity(intent)
        }
        else if(selectedOpiton == "tarifEkle"){
            val intent = Intent(applicationContext,TarifEkle::class.java)
            intent.putExtra("kullaniciAdi", kullaniciAdi)
            startActivity(intent)
        }
        else if(selectedOpiton == "tarifAl"){
            val intent = Intent(applicationContext,TarifAl::class.java)
            intent.putExtra("kullaniciAdi", kullaniciAdi)
            startActivity(intent)
        }
        if(selectedOpiton != null){
            finish()
        }

        // @@@@@@  SAYFA GEÇİŞLERİ
        return super.onOptionsItemSelected(item)
    }

    // API' den tarifleri getirir
    fun getRecipes(url: String){
        recipes.clear()
        val request =
            JsonArrayRequest(Request.Method.GET, url, null, Response.Listener { response ->
                for (i in 0 until response.length()) {
                    getRecipeDetail(response.getJSONObject(i), recipes)
                }
                recyclerView.layoutManager = LinearLayoutManager(this)
                val adapter = RecipesAdapter(recipes)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()

            }, Response.ErrorListener { error ->
                Toast.makeText(applicationContext, "Tarif Getirilemedi", Toast.LENGTH_SHORT).show()
            })

        MySingleton.getInstance(this).addToRequestQueue(request)
    }

    // API' den tarif detaylarını getirir
    fun getRecipeDetail(item: JSONObject, recipeList: ArrayList<RecipeModel>){

        val recipeName = item.getString("title")
        var ingredientList = ArrayList<String>()

        val usedIngredients = item.getJSONArray("usedIngredients")
        for (x in 0 until usedIngredients.length()) {
            val ingredient = usedIngredients.getJSONObject(x)
            val ingredientName = ingredient.getString("name")
            ingredientList.add(ingredientName)
        }

        val missedIngredients = item.getJSONArray("missedIngredients")
        for (x in 0 until missedIngredients.length()) {
            val ingredient = missedIngredients.getJSONObject(x)
            val ingredientName = ingredient.getString("name")
            ingredientList.add(ingredientName)
        }

        var imageUrl = item.getString("image")

        val imageRequest = ImageRequest(
            imageUrl,
            { bitmap ->
                val recipe = RecipeModel(recipeName, bitmap, ingredientList)
                recipeList.add(recipe)
            },
            0, // max width
            0, // max height
            ImageView.ScaleType.FIT_START, // image scale type
            Bitmap.Config.ARGB_8888, // decode config
            { error -> // error listener
                error.printStackTrace()
            }
        )
        MySingleton.getInstance(applicationContext)
            .addToRequestQueue(imageRequest)
    }
}