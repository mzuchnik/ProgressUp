package com.example.progressup

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_view.view.*

class HistoryAdapter(private val data : ArrayList<HistoryRowData>) :
    RecyclerView.Adapter<HistoryAdapter.MyViewHolder>() {


    class MyViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val historyRow = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_view, parent, false) as View

        return MyViewHolder(historyRow)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val name : TextView = holder.view.userNameHistory
        val date : TextView = holder.view.createDateHistory
        val moreInfo : Button = holder.view.moreInfoButton

        name.text = "Wymiary z dnia:"
        date.text = data[position].date
        moreInfo.text = "Wiecej "

        var id = data[position].id

        moreInfo.setOnClickListener(View.OnClickListener {
            Log.d("Wciśnięto: ",""+position)
            var intent = Intent(holder.view.context,HistoryData::class.java)
            intent.putExtra("userPlanMonitorId",id)
            intent.putExtra("date",data[position].date)
            ContextCompat.startActivity(holder.view.context,intent,null)

        })

    }


}