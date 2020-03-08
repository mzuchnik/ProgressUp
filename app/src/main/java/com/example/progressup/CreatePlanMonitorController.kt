package com.example.progressup

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.progressup.entity.PlanMonitor
import com.example.progressup.entity.UserPlanMonitor
import java.text.SimpleDateFormat
import java.util.*


class CreatePlanMonitorController : AppCompatActivity() {

    private lateinit var photoSwitch : Button
    private val REQUEST_CODE = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_plan_monitor_controller)

        // Przypisuje switche do wartosci stałych
        photoSwitch = findViewById(R.id.PhotoSwitch)
        val armSwitch = findViewById<EditText>(R.id.armSwitch)
        val calfSwitch = findViewById<EditText>(R.id.calfSwitch)
        val chestSwitch = findViewById<EditText>(R.id.chestSwitch)
        val forearmSwitch = findViewById<EditText>(R.id.forearmSwitch)
        val hipsSwitch = findViewById<EditText>(R.id.hipsSwitch)
        val succeedSwitch = findViewById<EditText>(R.id.succeedSwitch)
        val stomachSwitch = findViewById<EditText>(R.id.stomachSwitch)

        // Przycisk zatwierdzajacy ustawienia
        val savePlanMonitorButton = findViewById<Button>(R.id.savePlanMonitorButton)


        // UserId
        var userId = intent.getIntExtra("userId",0)

        savePlanMonitorButton.setOnClickListener(View.OnClickListener {

            var arm = armSwitch.text.toString()
            var calf = calfSwitch.text.toString()
            var chest = chestSwitch.text.toString()
            var forearm = forearmSwitch.text.toString()
            var hips = hipsSwitch.text.toString()
            var succeed = succeedSwitch.text.toString()
            var stomach = stomachSwitch.text.toString()
            var photoURI = photoSwitch.hint.toString()


            var db = DatabaseAccess.getInstance(applicationContext)
            db.open()

            var values = ContentValues()

            values.put(PlanMonitor.COLUMN_ARM, arm)
            values.put(PlanMonitor.COLUMN_CALF, calf)
            values.put(PlanMonitor.COLUMN_CHEST, chest)
            values.put(PlanMonitor.COLUMN_FOREARM, forearm)
            values.put(PlanMonitor.COLUMN_HIPS, hips)
            values.put(PlanMonitor.COLUMN_SUCCEED, succeed)
            values.put(PlanMonitor.COLUMN_STOMACH, stomach)
            values.put(PlanMonitor.COLUMN_PHOTO_URI, photoURI)

            var planMonitorId = db.insert(PlanMonitor.TABLE_NAME, null, values)

            var userPlanMonitor = ContentValues()

            var sdf = SimpleDateFormat("yyyy-MM-dd")
            var date: String = sdf.format(Date())

            userPlanMonitor.put(UserPlanMonitor.COLUMN_ID_USER,userId.toString())
            userPlanMonitor.put(UserPlanMonitor.COLUMN_ID_PLAN_MONITOR, planMonitorId)
            userPlanMonitor.put(UserPlanMonitor.COLUMN_CREATE_DATE,date)

            db.insert(UserPlanMonitor.TABLE_NAME,null,userPlanMonitor)

            var cursor = db.rawQuery("SELECT * FROM "+UserPlanMonitor.TABLE_NAME, arrayOf())

            cursor.moveToLast()

            Log.d("UserPlanMonitor","id: "+cursor.getInt(cursor.getColumnIndexOrThrow(UserPlanMonitor.COLUMN_ID)))
            Log.d("UserPlanMonitor","userId: "+cursor.getInt(cursor.getColumnIndexOrThrow(UserPlanMonitor.COLUMN_ID_USER)))
            Log.d("UserPlanMonitor","planId: "+cursor.getInt(cursor.getColumnIndexOrThrow(UserPlanMonitor.COLUMN_ID_PLAN_MONITOR)))
            Log.d("UserPlanMonitor","create: "+cursor.getString(cursor.getColumnIndexOrThrow(UserPlanMonitor.COLUMN_CREATE_DATE)))


            db.close()
            finish()
        })

        photoSwitch.setOnClickListener(View.OnClickListener {
            var intent = Intent(applicationContext, CameraController::class.java)
            startActivityForResult(intent,REQUEST_CODE)
        })

        Log.d("context: ",""+baseContext)



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE)
        {
            if (data != null) {
                if(data.hasExtra("photoURI")){
                    photoSwitch.hint = data.getStringExtra("photoURI")
                    Log.d("photoURI",photoSwitch.hint.toString())
                }
            }

        }
    }
}
