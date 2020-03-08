package com.example.progressup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.progressup.entity.User
import kotlinx.android.synthetic.main.activity_main_menu.*
import kotlinx.android.synthetic.main.activity_registry_controller.*

class UserDetailsController : AppCompatActivity() {

    private lateinit var button : Button
    private lateinit var name : EditText
    private lateinit var surname : EditText
    private lateinit var login : EditText
    private lateinit var password : EditText
    private lateinit var registryTitle : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry_controller)

        button = findViewById(R.id.registryButton)
        name = findViewById(R.id.nameText)
        surname = findViewById(R.id.surnameText)
        login = findViewById(R.id.loginText)
        password = findViewById(R.id.passwordText)
        registryTitle = findViewById(R.id.registryTitle)

        button.text = "Usuń użytkownika"
    }

    override fun onResume() {
        super.onResume()


        var userId = intent.getIntExtra("userId", 0)

        registryTitle.text = "Użytkownik o id: $userId"

        var db = DatabaseAccess.getInstance(applicationContext)

        db.open()

        var cursor = db.rawQuery("Select * FROM users WHERE id = ?", arrayOf(userId.toString()))

        cursor.moveToFirst()

        var nameUser = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_NAME))
        var surnameUser = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_SURNAME))
        var passwordUser = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_PASSWORD))
        var loginUser = cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_LOGIN))

        name.setText(nameUser)
        surname.setText(surnameUser)
        password.setText(passwordUser)
        login.setText(loginUser)

        // usuwa użytkownika
        button.setOnClickListener(View.OnClickListener {
            db.delete(User.TABLE_NAME,"id = ?", arrayOf(userId.toString()))
            Toast.makeText(application,"Usunięto: $loginUser",Toast.LENGTH_SHORT).show()
            onBackPressed()
        })

    }
}
