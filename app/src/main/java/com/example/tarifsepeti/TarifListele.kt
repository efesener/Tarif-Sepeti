package com.example.tarifsepeti

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_tarif_listele.*
import java.lang.Exception

class TarifListele : AppCompatActivity() {

    var yemekIsmiListesi = ArrayList<String>()
    var yemekIdListesi = ArrayList<Int>()
    private lateinit var listeAdapter: ListeRecyclerAdapter

    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim
    var  kullaniciAdi = ""
    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarif_listele)

        listeAdapter = ListeRecyclerAdapter(yemekIsmiListesi,yemekIdListesi)  // adapteri bağlamak için
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = listeAdapter

        VeriAlma()

        // @@@@@@ Kullanıcı giriş yaptıktan sonra tarif listeleye düşer ve intentten gelen kullaniciAdi bilgisi tanımlanır
        val intent = intent
        kullaniciAdi = intent.getStringExtra("kullaniciAdi").toString()

       // txtKullaniciAdi.text = kullaniciAdi
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


    fun VeriAlma() {
        try{
            val database = this.openOrCreateDatabase("Yemekler",Context.MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM yemekler WHERE kullaniciAdi =${kullaniciAdi}",null)
            val yemekIsmiIndex = cursor.getColumnIndex("yemekismi")
            val yemekIdIndex = cursor.getColumnIndex("id")

            yemekIsmiListesi.clear()
            yemekIdListesi.clear()

            while (cursor.moveToNext()){
                yemekIsmiListesi.add(cursor.getString(yemekIsmiIndex))
                yemekIdListesi.add(cursor.getInt(yemekIdIndex))

            }
              listeAdapter.notifyDataSetChanged()   //veri değişikliğinde güncellenmesi için
              cursor.close()

        }
        catch(e:Exception){
            e.printStackTrace()
        }
    }
}