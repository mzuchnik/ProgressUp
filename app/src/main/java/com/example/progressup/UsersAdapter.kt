package com.example.progressup

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_view.view.*

class UsersAdapter(private val data : ArrayList<UserRowData>) :
    RecyclerView.Adapter<UsersAdapter.MyUserViewHolder>() {


    class MyUserViewHolder(val view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyUserViewHolder {
        val historyRow = LayoutInflater.from(parent.context)
            .inflate(R.layout.history_view, parent, false) as View

        return MyUserViewHolder(historyRow)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holderUser: MyUserViewHolder, position: Int) {

        val name : TextView = holderUser.view.userNameHistory
        val date : TextView = holderUser.view.createDateHistory
        val moreInfo : Button = holderUser.view.moreInfoButton

        name.text = data[position].name
        date.text = data[position].surname
        moreInfo.text = data[position].login

        var id = data[position].id

        moreInfo.setOnClickListener(View.OnClickListener {
            var intent = Intent(holderUser.view.context,UserDetailsController::class.java)
            intent.putExtra("userId",id)
            ContextCompat.startActivity(holderUser.view.context,intent,null)
        })

    }


}