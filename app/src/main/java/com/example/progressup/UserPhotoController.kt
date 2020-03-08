package com.example.progressup

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import java.io.File

class UserPhotoController : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_photo_controller)

        var photoURI = intent.getStringExtra("photoURI")

        var userPhoto = findViewById<ImageView>(R.id.userPhoto)

        val imgFile = File(photoURI)

        if(imgFile.exists())
        {
            Log.d("photo","istnieje")
            val decodeFile : Bitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            userPhoto.setImageBitmap(decodeFile)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
