package com.example.progressup

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.progressup.entity.PlanAim
import com.example.progressup.entity.User

class MyPlanController : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_plan_controller)

        var toCreatePlanMonitorButton = findViewById<Button>(R.id.toCreatePlanButton)

        var gymGoalEditText = findViewById<EditText>(R.id.gymGoalEditText)
        var goalTypeEditText = findViewById<EditText>(R.id.goalTypeEditText)
        var weightCurrentEditText = findViewById<EditText>(R.id.weightCurrentEditText);
        var weightAimEditText = findViewById<EditText>(R.id.weightAimEditText)
        var dateAim = findViewById<EditText>(R.id.dateAim)
        var remind = findViewById<EditText>(R.id.remindEditText)

        var userId = intent.getIntExtra("userId", 0)

        findViewById<TextView>(R.id.myPlanStep1).setText("Twoja aktywność:")
        findViewById<TextView>(R.id.myPlanStep2).setText("Twój cel:")
        findViewById<TextView>(R.id.myPlanStep3).setText("Twój czas do celu:")

        toCreatePlanMonitorButton.setText("Aktualizuj")


        toCreatePlanMonitorButton.setOnClickListener(View.OnClickListener {
            val values = ContentValues()

            var db = DatabaseAccess.getInstance(applicationContext)
            db.open()

            values.put(PlanAim.COLUMN_ACTIVITY, gymGoalEditText.text.toString())
            values.put(PlanAim.COLUMN_AIM, goalTypeEditText.text.toString())
            values.put(PlanAim.COLUMN_WEIGHT_CURRENT, weightCurrentEditText.text.toString())
            values.put(PlanAim.COLUMN_WEIGHT_AIM,weightAimEditText.text.toString())
            values.put(PlanAim.COLUMN_DATE_AIM,dateAim.text.toString())
            values.put(PlanAim.COLUMN_REMIND, remind.text.toString())

            val planId = db.insert(PlanAim.TABLE_NAME,null,values)

            var userIdPlanAimUpdate = ContentValues()

            userIdPlanAimUpdate.put(User.COLUMN_ID_PLAN_AIM, planId)

            db.update(User.TABLE_NAME,userIdPlanAimUpdate,User.COLUMN_ID+" LIKE ? ", arrayOf(userId.toString()))

            db.close()
        })
    }

    override fun onResume() {
        super.onResume()

        var gymGoalEditText = findViewById<EditText>(R.id.gymGoalEditText)
        var goalTypeEditText = findViewById<EditText>(R.id.goalTypeEditText)
        var weightCurrentEditText = findViewById<EditText>(R.id.weightCurrentEditText);
        var weightAimEditText = findViewById<EditText>(R.id.weightAimEditText)
        var dateAim = findViewById<EditText>(R.id.dateAim)
        var remind = findViewById<EditText>(R.id.remindEditText)

        var userId = intent.getIntExtra("userId", 0)

        var db = DatabaseAccess.getInstance(applicationContext)

        db.open()

        var cursor = db.rawQuery("SELECT p.* FROM " + PlanAim.TABLE_NAME + " p JOIN " + User.TABLE_NAME + " u ON u." +
                User.COLUMN_ID_PLAN_AIM + " = p." + PlanAim.COLUMN_ID + " WHERE u."+User.COLUMN_ID + " LIKE ?",
            arrayOf(userId.toString()))


        cursor.moveToFirst()

        gymGoalEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(PlanAim.COLUMN_ACTIVITY)))
        goalTypeEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(PlanAim.COLUMN_AIM)))
        weightAimEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(PlanAim.COLUMN_WEIGHT_CURRENT)))
        weightCurrentEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow(PlanAim.COLUMN_WEIGHT_AIM)))
        dateAim.setText(cursor.getString(cursor.getColumnIndexOrThrow(PlanAim.COLUMN_DATE_AIM)))
        remind.setText(cursor.getString(cursor.getColumnIndexOrThrow(PlanAim.COLUMN_REMIND)))

        db.close()
    }
}
