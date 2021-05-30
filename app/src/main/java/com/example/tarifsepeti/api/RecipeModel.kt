package com.example.tarifsepeti.api

import android.graphics.Bitmap

// API' den çektiğimiz veriler için model
data class RecipeModel (
    var isim: String? = "",
    var image: Bitmap,
    var malzemeler: ArrayList<String>
)