package com.example.progressup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progressup.entity.User
import com.example.progressup.entity.UserPlanMonitor

class HistoryController : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_controller)

        val db = DatabaseAccess.getInstance(applicationContext)

        var userId = intent.getIntExtra("userId",0)

        db.open()

        var cursor = db.rawQuery("SELECT * FROM userPlanMonitor up JOIN users u ON u.id=up.idUser WHERE up.idUser = ? ORDER BY up.createDate DESC",
            arrayOf(userId.toString()))


        val historyRowsData = arrayListOf<HistoryRowData>()

        cursor.moveToFirst()
        while (!cursor.isAfterLast)
        {
            historyRowsData.add(
                HistoryRowData(cursor.getString(cursor.getColumnIndexOrThrow(UserPlanMonitor.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(User.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(UserPlanMonitor.COLUMN_CREATE_DATE))
                )
            )
            cursor.moveToNext()
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = HistoryAdapter(historyRowsData)


        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply{

            setHasFixedSize(true)

            layoutManager =viewManager

            adapter = viewAdapter
        }
        db.close()
    }

}
