package com.example.progressup

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.database.getIntOrNull
import com.example.progressup.entity.User
import com.example.progressup.entity.UserActive
import kotlinx.android.synthetic.main.activity_main_menu.*


class MainMenuActivity : AppCompatActivity() {

    var userId = -1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        val createPlanButton = findViewById<Button>(R.id.createPlanButton)
        val logoutButton = findViewById<Button>(R.id.logoutButton)
        val myPlanButton = findViewById<Button>(R.id.myPlanButton);
        val addPlanMonitor = findViewById<Button>(R.id.addPlanMonitorButton)
        val historyButton =  findViewById<Button>(R.id.historyButton)
        val howToFitButton = findViewById<Button>(R.id.howToFitButton)
        val statsButton = findViewById<Button>(R.id.statsButton)
        val toUsersButton = findViewById<Button>(R.id.toUsersButton)

        val userName = findViewById<TextView>(R.id.userName)

        userId = intent.getIntExtra("userId",0)
        var role = intent.getIntExtra("userRole",0)

        var db = DatabaseAccess.getInstance(applicationContext);
        db.open()
        var userNameFromDB = db.getUserNameById(userId)
        userName.text = userNameFromDB
        db.close()

        // Obsługa przycisku createPlanButton -> rozpoczyna i przenosi do nowej aktywności
        createPlanButton.setOnClickListener(View.OnClickListener {
            var toCreatePlan = Intent(applicationContext, CreatePlanController::class.java)
            toCreatePlan.putExtra("userId",userId)
            startActivity(toCreatePlan)
        })

        // Pokazuje mój plan
        myPlanButton.setOnClickListener(View.OnClickListener {
            var toMyPlan = Intent(applicationContext,MyPlanController::class.java)
            toMyPlan.putExtra("userId",userId)
            startActivity(toMyPlan)
        })

        // Dodanie swoich osiagniec
        addPlanMonitorButton.setOnClickListener(View.OnClickListener {
            var toPlanMonitor = Intent(applicationContext,CreatePlanMonitorController::class.java)
            toPlanMonitor.putExtra("userId",userId)
            startActivity(toPlanMonitor)
        })

        // Wyświetlenie histori
        historyButton.setOnClickListener(View.OnClickListener {
            var toHistoryList = Intent(applicationContext, HistoryController::class.java)
            toHistoryList.putExtra("userId",userId)
            startActivity(toHistoryList)
        })

        // Jak się mierzyć?
        howToFitButton.setOnClickListener(View.OnClickListener {
            var toHowToFit = Intent(applicationContext, HowToFitController::class.java)
            startActivity(toHowToFit)
        })

        // Statystyki
        statsButton.setOnClickListener(View.OnClickListener {
            var toStatsIntent = Intent(applicationContext, StatsController::class.java)
            toStatsIntent.putExtra("userId",userId)
            startActivity(toStatsIntent)
        })

        // Zarządzaj użytkownikami
        if(role ==1){toUsersButton.visibility = View.VISIBLE} else {toUsersButton.visibility = View.GONE}
        toUsersButton.setOnClickListener(View.OnClickListener {
            var toUsersIntent = Intent(applicationContext, UsersController::class.java)
            startActivity(toUsersIntent)
        })

        // Wylogowanie użytkownika wraz z odpowiednim komunikatem
        logoutButton.setOnClickListener(View.OnClickListener {
            var db = DatabaseAccess.getInstance(applicationContext)
            db.open()
            var values = ContentValues()
            values.put(UserActive.COLUMN_USER_ID,0)
            db.update(UserActive.TABLE_NAME,values,"${UserActive.COLUMN_USER_ID} LIKE ? ", arrayOf(userId.toString()))

            db.close()
            var toLoginPage = Intent(applicationContext, LoginActivity::class.java)
            toLoginPage.putExtra("logoutMessage","Wylogowano pomyślnie")
            finish()
            startActivity(toLoginPage)
        })

    }

    override fun onResume() {
        super.onResume()
        checkIfUserHaveAim()
    }

    // Nadpisuje przycisk cofniecia, tak by nic nie robił
    override fun onBackPressed() {

    }

    // Sprawdza czy użytkownik ma ustawiony cel jeśli tak to wyświet przycisk "Moj plan"
    private fun checkIfUserHaveAim()
    {
        val db = DatabaseAccess.getInstance(applicationContext)
        db.open()
        var cursor : Cursor = db.rawQuery("SELECT * FROM users WHERE id = ?", arrayOf(userId.toString()))
        cursor.moveToFirst()
        Log.d("Database",""+ cursor.getInt(0))
        if (cursor.getInt(cursor.getColumnIndexOrThrow(User.COLUMN_ID_PLAN_AIM)) == 0)
        {
            addPlanMonitorButton.visibility = View.GONE
            myPlanButton.visibility = View.GONE
        }else {
            addPlanMonitorButton.visibility = View.VISIBLE
            myPlanButton.visibility = View.VISIBLE
        }
        db.close()
    }
}
