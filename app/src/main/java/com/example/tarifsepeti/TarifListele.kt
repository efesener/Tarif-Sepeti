package com.example.tarifsepeti

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_tarif_listele.*

class TarifListele : AppCompatActivity() {
    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim
    var  kullaniciAdi = ""
    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarif_listele)

        // @@@@@@ Kullanıcı giriş yaptıktan sonra tarif listeleye düşer ve intentten gelen kullaniciAdi bilgisi tanımlanır
        val intent = intent
        kullaniciAdi = intent.getStringExtra("kullaniciAdi").toString()

        txtKullaniciAdi.text = kullaniciAdi
        // @@@@@@ Kullanıcı giriş yaptıktan sonra tarif listeleye düşer ve intentten gelen kullaniciAdi bilgisi tanımlanır

    }


    // @@@@@@ MENU ILE İLGİLİ FONKSİYONLAR
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.getItem(0)?.setEnabled(false) // Menüdeki bir itemi deaktive etme
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var selectedOpiton = ""

        when(item.itemId){
            R.id.tarifListele -> selectedOpiton = "tarifListele"
            R.id.tarifEkle -> selectedOpiton = "tarifEkle"
            R.id.tarifAl -> selectedOpiton = "tarifAl"
        }


        // @@@@@@  SAYFA GEÇİŞLERİ
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


    // @@@@@@ MENU ILE İLGİLİ FONKSİYONLAR
}