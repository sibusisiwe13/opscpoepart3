package com.example.opscpart3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class TimesheetList : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var Timesheets: MutableList<Timesheet>
    private lateinit var timesheetAdapter: TimesheetAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var btnEntries: Button
    private lateinit var taskname:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timesheetlist)

        db = FirebaseFirestore.getInstance()
        Timesheets = mutableListOf()
        timesheetAdapter = TimesheetAdapter(Timesheets)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = timesheetAdapter
        btnEntries.setOnClickListener { fetchTasks() }
    }

    private fun fetchTasks() {
        val startdate = taskname.text.toString()
        db.collection("timesheet")
            .whereEqualTo("Date", startdate)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    val entry = document.toObject(Timesheet::class.java)
                    Timesheets.add(entry)
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }

    }
}