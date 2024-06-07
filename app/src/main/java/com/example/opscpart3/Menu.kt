package com.example.opscpart3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class Menu : AppCompatActivity() {


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.menu)

        val timesheetbtn: Button = findViewById(R.id.timesheetbtn)
        timesheetbtn.setOnClickListener {
            val intent = Intent(this,Timesheet::class.java)
            startActivity(intent)
        }
        val settingbtn: Button = findViewById(R.id.settingbtn)
        settingbtn.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }


        val chatsbtn: Button = findViewById(R.id.chatsbtn)
        chatsbtn.setOnClickListener {
            val intent = Intent(this, Chats::class.java)
            startActivity(intent)
        }

        val invoicebtn: Button = findViewById(R.id.invoicebtn)
        invoicebtn.setOnClickListener {
            val intent = Intent(this, Invoice::class.java)
            startActivity(intent)
        }

    }

}