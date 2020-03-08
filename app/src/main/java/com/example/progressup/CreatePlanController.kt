package com.example.progressup


import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.progressup.entity.PlanAim
import com.example.progressup.entity.User


class CreatePlanController : AppCompatActivity() {

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

        var userId = intent.getIntExtra("userId",0)


        toCreatePlanMonitorButton.setOnClickListener(View.OnClickListener {

            val db = DatabaseAccess.getInstance(applicationContext)


            val values = ContentValues()

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

            var toCreatePlanMonitorIntent = Intent(applicationContext,CreatePlanMonitorController::class.java)
            toCreatePlanMonitorIntent.putExtra("userId",userId)
            finish()
            startActivity(toCreatePlanMonitorIntent)

        })
    }
}
