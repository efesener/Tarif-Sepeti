package com.example.tarifsepeti

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_tarif_detay.*
import kotlinx.android.synthetic.main.activity_tarif_ekle.*
import kotlinx.android.synthetic.main.activity_tarif_listele.*
import java.io.ByteArrayOutputStream
import java.lang.Exception

class TarifDetay : AppCompatActivity() {



    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim
    var  kullaniciAdi = ""
    var id = 1
    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarif_detay)

        // @@@@@@ Bir önceki sayfadan gelen kullaniciAdi bilgisini alıyoruz
        val intent = intent
        id = intent.getIntExtra("Id", 1)

        //txtKullaniciAdi2.text = kullaniciAdi
        // @@@@@@ Bir önceki sayfadan gelen kullaniciAdi bilgisini alıyoruz

        detay()

    }

    fun detay() {
        try{
            val database = this.openOrCreateDatabase("Yemekler",Context.MODE_PRIVATE,null)
            val cursor = database.rawQuery("SELECT * FROM yemekler WHERE id = $id",null) //WHERE

            val yemekIsmiIndex = cursor.getColumnIndex("yemekismi")
            val yemekMalzemeleriIndex= cursor.getColumnIndex("malzemeler")
            val yemekTarifiIndex=cursor.getColumnIndex("tarif")
            val yemekGorsel=cursor.getColumnIndex("gorsel")
            val kullaniciIndex = cursor.getColumnIndex("kullaniciadi");


            while (cursor.moveToNext()){
                yemekIsim.text= cursor.getString(yemekIsmiIndex).toString()
                yemekMalzemeler.text=cursor.getString(yemekMalzemeleriIndex).toString()
                yemekTarif.text=cursor.getString(yemekTarifiIndex).toString()
                kullaniciAdi = cursor.getString((kullaniciIndex)).toString()

                val byteDizisi = cursor.getBlob(yemekGorsel)
                val bitmap=BitmapFactory.decodeByteArray(byteDizisi,0,byteDizisi.size)
                yemekResim.setImageBitmap(bitmap)
                }

            cursor.close()

        }
        catch(e: Exception){
            e.printStackTrace()
        }
    }
    // @@@@@@ MENU ILE İLGİLİ FONKSİYONLAR
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
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