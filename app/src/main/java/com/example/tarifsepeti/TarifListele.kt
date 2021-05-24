package com.example.tarifsepeti

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_tarif_listele.*

class TarifListele : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarif_listele)

        // giriş yapan kullanıcının adını al
        val intent = intent
        val kullaniciAdi = intent.getStringExtra("kullaniciAdi")

        txtKullaniciAdi.text = kullaniciAdi
        // giriş yapan kullanıcının adını al


    }
}