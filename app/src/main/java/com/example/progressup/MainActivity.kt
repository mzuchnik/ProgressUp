package com.example.progressup

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.progressup.entity.UserActive

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //applicationContext.deleteDatabase("database.db")

        var db = DatabaseAccess.getInstance(applicationContext)
        db.open()

        var cursor = db.rawQuery("SELECT * FROM ${UserActive.TABLE_NAME}", arrayOf())

        cursor.moveToFirst()

        var isUserLogged = cursor.getInt(cursor.getColumnIndexOrThrow(UserActive.COLUMN_USER_ID))

        Handler().postDelayed(
            {
                if(isUserLogged == 0) {
                    var intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                }else
                {
                    var toMainMenu = Intent(applicationContext, MainMenuActivity::class.java)
                    toMainMenu.putExtra("userId",isUserLogged)
                    startActivity(toMainMenu)
                }
            }
        ,2000)
    }


}
