package com.example.tarifsepeti

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_tarif_ekle.*
import kotlinx.android.synthetic.main.activity_tarif_listele.*
import java.io.ByteArrayOutputStream

class TarifEkle : AppCompatActivity() {
    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim
    var  kullaniciAdi = ""
    // @@@@@@ kullanıcı adını global değişken olarak tanımlıyorum ki her fonksiyon içinde kullanabileyim

    var eklenenGorsel : Uri? = null
    var secilenBitmap : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tarif_ekle)

        // @@@@@@ Bir önceki sayfadan gelen kullaniciAdi bilgisini alıyoruz
        val intent = intent
        kullaniciAdi = intent.getStringExtra("kullaniciAdi").toString()

        txtKullaniciAdi3.text = kullaniciAdi
        // @@@@@@ Bir önceki sayfadan gelen kullaniciAdi bilgisini alıyoruz

        //println("sude")
    }
    
    // @@@@@@ MENU ILE İLGİLİ FONKSİYONLAR
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.getItem(1)?.setEnabled(false) // Menüdeki bir itemi deaktive etme
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

        //  @@@@@@  SAYFA GEÇİŞLERİ
        return super.onOptionsItemSelected(item)
    }

    // @@@@@@ MENU ILE İLGİLİ FONKSİYONLAR

    fun ekle(view:View){
        Toast.makeText(applicationContext, "Ürün Başarıyla Eklendi", Toast.LENGTH_SHORT).show()
        val yemekIsmi = yemekAdiText.text.toString()
        val yemekMalzemeleri = malzemelerText.text.toString()
        val yemekTarifi = tarifText.text.toString()

        if(secilenBitmap != null) {
            val kucukBitmap = bitmapKucult(secilenBitmap!!, 300)

            val outputStream = ByteArrayOutputStream()
            kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            val byteDizisi = outputStream.toByteArray()

            try {

                    val veritabani = this.openOrCreateDatabase("Yemekler", Context.MODE_PRIVATE,null)
                    veritabani.execSQL("CREATE TABLE IF NOT EXISTS yemekler (id INTEGER PRIMARY KEY , yemekismi VARCHAR, malzemeler VARCHAR, tarif VARCHAR, gorsel BLOB, kullaniciadi VARCHAR)")

                    val sqlString ="INSERT INTO yemekler(yemekismi, malzemeler, tarif, gorsel, kullaniciadi) VALUES (?,?,?,?,?)"
                    val statement = veritabani.compileStatement(sqlString)
                    statement.bindString(1,yemekIsmi)
                    statement.bindString(2,yemekMalzemeleri)
                    statement.bindString(3,yemekTarifi)
                    statement.bindBlob(4,byteDizisi)
                    statement.bindString(5,kullaniciAdi)
                    statement.execute()

            }catch (e:Exception){
                e.printStackTrace()
            }

            // tarif listeleye gönderilecek
            fun tarifListele(){
                val intent = Intent(applicationContext,TarifListele::class.java)
                intent.putExtra("yemekAdi", yemekIsmi)
                intent.putExtra("yemekMalzeme", yemekMalzemeleri)
                intent.putExtra("yemekTarif", yemekTarifi)
                startActivity(intent)
            }
            tarifListele()
        }
    }

    fun gorselEkle(view:View){

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

            //üstte izin verilmediyse tekrar izin istedim
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)

        } else { //izin verilmiş artık galeriye gitcem

            var galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent, 2)

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1){

            if(grantResults.size>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                var galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent, 2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode == 2 && resultCode == Activity.RESULT_OK && data !=null){

            eklenenGorsel = data.data
            if(eklenenGorsel != null){
                if(Build.VERSION.SDK_INT >= 28){

                    val source = ImageDecoder.createSource(this.contentResolver, eklenenGorsel!!)
                    secilenBitmap = ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(secilenBitmap)

                } else{

                    secilenBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,eklenenGorsel)
                    imageView.setImageBitmap(secilenBitmap)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    fun bitmapKucult(kulanicininSectigiBitmap : Bitmap, maxBoyut : Int) : Bitmap{
        var width = kulanicininSectigiBitmap.width
        var height = kulanicininSectigiBitmap.height

        val bitmapOran : Double = width.toDouble() / height.toDouble()

        if(bitmapOran > 1){
            //görsel yatay
            width = maxBoyut
            val kucukheight = width / bitmapOran
            height = kucukheight.toInt()
        }
        else{
            //görsel dikey
            height = maxBoyut
            val kucukwidth = height * bitmapOran
            width = kucukwidth.toInt()
        }
        return Bitmap.createScaledBitmap(kulanicininSectigiBitmap,width,height,true)
    }
}