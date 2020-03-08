package com.example.progressup

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import com.example.progressup.entity.UserActive

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Pobieranie widoków z szablonu
        val login = findViewById<EditText>(R.id.loginEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val badLoginPass = findViewById<TextView>(R.id.wrongLoginPass)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val logoutTextView = findViewById<TextView>(R.id.logoutTextView)
        var registryButton = findViewById<Button>(R.id.registryButton)
        var rememberMeCheckBox =  findViewById<CheckBox>(R.id.rememberMeCheckBox)

        //Pobieranie dodatkowych danych poprzez Intent
        val logoutMessage = intent.getStringExtra("logoutMessage")



        if (logoutMessage != null)
        {
            logoutTextView.visibility = View.VISIBLE
            logoutTextView.text = logoutMessage
        }


        loginButton.setOnClickListener(View.OnClickListener {

            var loginText : String? = login.text.toString()
            var passwordText : String? = password.text.toString()

            var db = DatabaseAccess.getInstance(applicationContext)
            db.open()

            var cursor : Cursor = db.rawQuery("SELECT id, role FROM users WHERE login= ? AND password= ?", arrayOf(loginText,passwordText))

            if (cursor.count != 0)
            {
                cursor.moveToFirst()
                var userId = cursor.getInt(0)
                var userRole = cursor.getInt(1)

                if(rememberMeCheckBox.isChecked) {
                    var values = ContentValues()
                    values.put(UserActive.COLUMN_USER_ID, userId)
                    db.update(
                        UserActive.TABLE_NAME, values, "${UserActive.COLUMN_USER_ID} LIKE ?",
                        arrayOf("0")
                    )
                    db.close()
                }
                val toLoginActivity = Intent(applicationContext,MainMenuActivity::class.java)
                toLoginActivity.putExtra("userId",userId)
                toLoginActivity.putExtra("userRole",userRole)
                finish()
                startActivity(toLoginActivity)

            }else
            {
                badLoginPass.visibility = View.VISIBLE
                logoutTextView.visibility = View.GONE
            }

        })

        registryButton.setOnClickListener(View.OnClickListener {
            var intent = Intent(applicationContext,RegistryController::class.java)
            startActivity(intent)
        })
    }

    override fun onBackPressed() {

    }
}
