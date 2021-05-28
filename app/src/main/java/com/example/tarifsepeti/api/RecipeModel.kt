package com.example.tarifsepeti.api

import android.graphics.Bitmap

data class RecipeModel (
    var isim: String? = "",
    var image: Bitmap,
    var malzemeler: ArrayList<String>
)