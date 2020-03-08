package com.example.progressup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progressup.entity.User
import com.example.progressup.entity.UserPlanMonitor

class UsersController : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var userRowsData : ArrayList<UserRowData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users_controller)

    }

    override fun onResume() {
        super.onResume()

        val db = DatabaseAccess.getInstance(applicationContext)

        //var userId = intent.getIntExtra("userId",0)

        db.open()

        // Pobiera wszystkich użytkowników
        var cursor = db.rawQuery("SELECT id, login, name, surname FROM users", arrayOf())

        userRowsData = arrayListOf<UserRowData>()

        cursor.moveToFirst()
        while (!cursor.isAfterLast)
        {
            userRowsData.add(
                UserRowData(cursor.getInt(cursor.getColumnIndexOrThrow(User.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_LOGIN)),
                    cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_SURNAME))
                )
            )
            cursor.moveToNext()
        }


        viewManager = LinearLayoutManager(this)
        viewAdapter = UsersAdapter(userRowsData)


        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_users_view).apply{

            setHasFixedSize(true)

            layoutManager =viewManager

            adapter = viewAdapter
        }
        db.close()
    }
}
