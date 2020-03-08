package com.example.progressup

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.progressup.entity.PlanAim
import com.example.progressup.entity.PlanMonitor

class HistoryData : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_plan_monitor_controller)

        // Przypisuje switche do wartosci stałych
        val photoSwitch = findViewById<Button>(R.id.PhotoSwitch)
        val armSwitch = findViewById<EditText>(R.id.armSwitch)
        val calfSwitch = findViewById<EditText>(R.id.calfSwitch)
        val chestSwitch = findViewById<EditText>(R.id.chestSwitch)
        val forearmSwitch = findViewById<EditText>(R.id.forearmSwitch)
        val hipsSwitch = findViewById<EditText>(R.id.hipsSwitch)
        val succeedSwitch = findViewById<EditText>(R.id.succeedSwitch)
        val stomachSwitch = findViewById<EditText>(R.id.stomachSwitch)
        val planMonitorText = findViewById<TextView>(R.id.planMonitorText)

        // Przycisk zatwierdzajacy ustawienia
        val savePlanMonitorButton = findViewById<Button>(R.id.savePlanMonitorButton)

        val userPlanMonitorId = intent.getStringExtra("userPlanMonitorId")
        val date = intent.getStringExtra("date")

        planMonitorText.text = "Wymiary z dnia "+date

        var db = DatabaseAccess.getInstance(applicationContext)
        db.open()

        var cursor = db.rawQuery("SELECT pm.* FROM userPlanMonitor upm JOIN planMonitor pm ON upm.idPlanMonitor = pm.idPlanMonitor WHERE upm.idUserPlanMonitor = ?",
            arrayOf(userPlanMonitorId))

        cursor.moveToFirst()

        armSwitch.setText(cursor.getInt(cursor.getColumnIndexOrThrow(PlanMonitor.COLUMN_ARM)).toString())
        calfSwitch.setText(cursor.getInt(cursor.getColumnIndexOrThrow(PlanMonitor.COLUMN_CALF)).toString())
        chestSwitch.setText(cursor.getInt(cursor.getColumnIndexOrThrow(PlanMonitor.COLUMN_CHEST)).toString())
        forearmSwitch.setText(cursor.getInt(cursor.getColumnIndexOrThrow(PlanMonitor.COLUMN_FOREARM)).toString())
        hipsSwitch.setText(cursor.getInt(cursor.getColumnIndexOrThrow(PlanMonitor.COLUMN_HIPS)).toString())
        succeedSwitch.setText(cursor.getInt(cursor.getColumnIndexOrThrow(PlanMonitor.COLUMN_SUCCEED)).toString())
        stomachSwitch.setText(cursor.getInt(cursor.getColumnIndexOrThrow(PlanMonitor.COLUMN_STOMACH)).toString())
        var photoURI = cursor.getString(cursor.getColumnIndexOrThrow(PlanMonitor.COLUMN_PHOTO_URI))

        savePlanMonitorButton.visibility = View.GONE

        db.close()

        photoSwitch.keyListener = null
        armSwitch.keyListener = null
        calfSwitch.keyListener = null
        chestSwitch.keyListener = null
        forearmSwitch.keyListener = null
        hipsSwitch.keyListener = null
        succeedSwitch.keyListener = null
        stomachSwitch.keyListener = null


        photoSwitch.text = "Zdjecie"

        photoSwitch.setOnClickListener(View.OnClickListener {
            var intent = Intent(applicationContext,UserPhotoController::class.java)
            intent.putExtra("photoURI",photoURI)
            startActivity(intent)

        })


    }

}
