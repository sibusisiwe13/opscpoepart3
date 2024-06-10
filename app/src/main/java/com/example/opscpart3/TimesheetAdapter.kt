package com.example.opscpart3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class TimesheetAdapter(private val timesheets: MutableList<Timesheet>):
    RecyclerView.Adapter<TimesheetAdapter.TimesheetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TimesheetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timesheet, parent, false)
        return TimesheetViewHolder(view)
    }

    override fun onBindViewHolder(holder: TimesheetViewHolder, position: Int) {
        val entry = timesheets[position]
        holder.bind(entry)
    }

    override fun getItemCount() = timesheets.size

    class TimesheetViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val startTime: EditText = itemView.findViewById(R.id.startTime)
        private val endTime: EditText = itemView.findViewById(R.id.endTime)
        private val date: EditText = itemView.findViewById(R.id.date)
        private val selectedCategory: EditText = itemView.findViewById(R.id.SelectedCategory)
        private val description: EditText = itemView.findViewById(R.id.description)
        private val photo: ImageView = itemView.findViewById(R.id.photo)

        fun bind(entry: Timesheet) {
            startTime.setText(entry.startTime)
            endTime.setText(entry.endTime)
            date.setText(entry.date)
            selectedCategory.setText(entry.category)
            description.setText(entry.description)
            if (entry.photoUrl != null) {
                photo.visibility = View.VISIBLE
                Picasso.get().load(entry.photoUrl).into(photo)
            } else {
                photo.visibility = View.GONE
            }
        }
    }
}
