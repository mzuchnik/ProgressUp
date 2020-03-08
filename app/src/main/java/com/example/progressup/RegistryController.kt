package com.example.progressup

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.progressup.entity.User
import org.w3c.dom.Text

class RegistryController : AppCompatActivity() {

    private lateinit var error : TextView
    private lateinit var button : Button
    private lateinit var name : EditText
    private lateinit var surname : EditText
    private lateinit var login : EditText
    private lateinit var password : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry_controller)

        error = findViewById(R.id.registryError)
        button = findViewById(R.id.registryButton)
        name = findViewById(R.id.nameText)
        surname = findViewById(R.id.surnameText)
        login = findViewById(R.id.loginText)
        password = findViewById(R.id.passwordText)

        var isNameOk = false
        var isLoginOk = false
        var isSurnameOk = false
        var isPasswordOk = false

        password.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (password.length() <= 6)
                {
                    error.setText("Hasło musi być dłuższe niż 6 znaków")
                    error.visibility = View.VISIBLE
                    button.isEnabled = false
                    isPasswordOk = false
                }else
                {
                    error.visibility = View.GONE
                    button.isEnabled = true
                    isPasswordOk = true
                }
            }
        })

        login.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (login.length() <= 2)
                {
                    error.setText("Login nie może być krótszy niż 2 znaki")
                    error.visibility = View.VISIBLE
                    button.isEnabled = false
                    isLoginOk = false
                }else
                {
                    error.visibility = View.GONE
                    button.isEnabled = true
                    isLoginOk = true
                }

            }
        })

        name.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(name.length() <= 2)
                {
                    error.setText("Imię nie może być krótsze niż 2 znaki")
                    error.visibility = View.VISIBLE
                    button.isEnabled = false
                    isNameOk = false
                }else
                {
                    error.visibility = View.GONE
                    button.isEnabled = true
                    isNameOk = true
                }
            }
        })

        surname.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(surname.length() <= 2)
                {
                    error.setText("Nazwisko nie może być krótsze niż 2 znaki")
                    error.visibility = View.VISIBLE
                    button.isEnabled = false
                    isSurnameOk = false
                }else
                {
                    error.visibility = View.GONE
                    button.isEnabled = true
                    isSurnameOk = true
                }
            }
        })

        button.setOnClickListener(View.OnClickListener {

            if(isLoginOk && isPasswordOk && isNameOk && isSurnameOk) {

                var db = DatabaseAccess.getInstance(applicationContext)
                db.open()

                val cursor = db.rawQuery(
                    "SELECT * FROM users WHERE login = ?",
                    arrayOf(login.text.toString())
                )
                if(cursor.count == 0) {
                    val values = ContentValues()

                    values.put(User.COLUMN_LOGIN, login.text.toString())
                    values.put(User.COLUMN_PASSWORD, password.text.toString())
                    values.put(User.COLUMN_NAME, name.text.toString())
                    values.put(User.COLUMN_SURNAME, surname.text.toString())
                    values.put(User.COLUMN_ROLE, 0)

                    db.insert(User.TABLE_NAME, null, values)

                    db.close()

                    finish()
                    onBackPressed()
                }else{
                    error.text="Podany login już istnieje"
                    error.visibility = View.VISIBLE
                }

                db.close()


            }else
            {
                error.text="Wprowadzono niepoprawne dane"
                error.visibility = View.VISIBLE
            }
        })
    }


}
