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

        // örnek tablo
        try {
            val veritabani = this.openOrCreateDatabase("Tarifler", Context.MODE_PRIVATE, null)
            veritabani.execSQL("CREATE TABLE IF NOT EXISTS tarifler (id INTEGER PRIMARY KEY, isim VARCHAR, fiyat INT)")

            /*
            veritabani.execSQL("INSERT INTO tarifler (isim, fiyat) VALUES ('Yumurta', 55)")
            veritabani.execSQL("INSERT INTO tarifler (isim, fiyat) VALUES ('Antrikot', 155)")
            veritabani.execSQL("INSERT INTO tarifler (isim, fiyat) VALUES ('Mkarna', 22)")
*/
            val cursor = veritabani.rawQuery("SELECT * FROM tarifler", null)

            val idColumnIndex = cursor.getColumnIndex("id")
            val isimColumnIndex = cursor.getColumnIndex("isim")
            val fiyatColumnIndex = cursor.getColumnIndex("fiyat")

            while(cursor.moveToNext()){
                println("ID : ${cursor.getInt(idColumnIndex)}")
                println("isim : ${cursor.getString(isimColumnIndex)}")
            }

            cursor.close()

        }catch (e: Exception){}

    }

    // örnek tablo son
}