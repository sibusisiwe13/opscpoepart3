package com.example.opscpart3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class TimesheetAdapter(private val entries: List<Timesheet>) : RecyclerView.Adapter<TimesheetAdapter.ViewHolder>(){
    inner class ViewHolder(taskView : View) : RecyclerView.ViewHolder(taskView) {
        val date: EditText = taskView.findViewById(R.id.date)
        val endTime: EditText = taskView.findViewById(R.id.endTime)
        val startTime: EditText = taskView.findViewById(R.id.startTime)
        val description: EditText = taskView.findViewById(R.id.description)
        val photo: ImageView = taskView.findViewById(R.id.photo)
        val SelectedCategory: EditText = taskView.findViewById(R.id.SelectedCategory)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimesheetAdapter.ViewHolder {

        val taskView = LayoutInflater.from(parent.context).inflate(R.layout.timesheet,parent,false)
        return ViewHolder(taskView)
    }

    override fun onBindViewHolder(holder: TimesheetAdapter.ViewHolder, position: Int) {
        val entry = entries[position]
        holder.date.text
        holder.endTime.text
        holder.startTime.text
        holder.description.text
        holder.SelectedCategory.text


    }

    override fun getItemCount(): Int {
        return entries.size

    }
}



