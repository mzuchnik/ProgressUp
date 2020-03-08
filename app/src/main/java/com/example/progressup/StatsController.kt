package com.example.progressup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import java.io.File

class StatsController : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats_controller)

        var createGifButton = findViewById<Button>(R.id.createGif)
        var gifId = findViewById<ImageView>(R.id.gifId)
        var delayEditText = findViewById<EditText>(R.id.delayEditText)

        var userId = intent.getIntExtra("userId",0)

        var paths : ArrayList<String> = ArrayList<String>()
        var gifPath : String

        // Tworzy i wyswietla gif
        createGifButton.setOnClickListener(View.OnClickListener {


            var db = DatabaseAccess.getInstance(applicationContext)

            db.open()

            var cursor = db.rawQuery("SELECT pm.photoURI FROM planMonitor pm join userPlanMonitor upm ON pm.idPlanMonitor = upm.idPlanMonitor WHERE upm.idUser = ?", arrayOf(userId.toString()))

            cursor.moveToFirst()
            while (!cursor.isAfterLast)
            {
                paths.add(cursor.getString(0))
                cursor.moveToNext()
            }

            db.close()

            var delay : Int
            var cg : CreateGif
            // Sprawdza czy delayEditText został ustawiony
            // jeśli tak to tworzy gif'a
            if(delayEditText.text.toString() != null) {
                delay = (delayEditText.text.toString()).toInt()
                cg = CreateGif(paths,delay)
            }else {
                cg = CreateGif(paths)
            }
            var gifPath = ""
            Thread(Runnable {
                Log.d("Gif","Przed")
                gifPath = cg.createGifAndGetPath()
                Log.d("Gif","Po")
                gifId.post(Runnable {
                    val imgFile = File(gifPath)

                    if(imgFile.exists())
                    {
                        //val decodeFile : Bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
                        Glide.with(this).load(imgFile).into(gifId)
                        //gifId.setImageBitmap(decodeFile)
                        Toast.makeText(applicationContext, "Stworzono gif: $gifPath",Toast.LENGTH_SHORT)
                    }
                })
            }).start()
        })



    }
}
