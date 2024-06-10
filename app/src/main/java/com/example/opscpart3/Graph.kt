package com.example.opscpart3

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*


class Graph : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private lateinit var pieChart: PieChart
    private var minHoursGoal = 160
    private var maxHoursGoal = 200


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graph)

        db = FirebaseFirestore.getInstance()
        pieChart = findViewById(R.id.pieChart)

        val btnReturn: Button = findViewById(R.id.btnReturn)
        btnReturn.setOnClickListener {
            val intent = Intent(this,Menu::class.java)
            startActivity(intent)
        }

        fetchGoals()
    }

    private fun fetchGoals() {
        db.collection("Goals")
            .document("user_goals")
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    minHoursGoal = document.getLong("minGoal")?.toInt() ?: minHoursGoal
                    maxHoursGoal = document.getLong("maxGoal")?.toInt() ?: maxHoursGoal
                }
                fetchData()
            }
    }

    private fun fetchData() {
        val date = Calendar.getInstance()
        date.add(Calendar.MONTH, -1)
        val start = date.time

        db.collection("Timesheets")
            .whereGreaterThan("date", start)
            .get()
            .addOnSuccessListener { result ->
                val timesheets = result.toObjects(Timesheets::class.java)
                showPieChart(timesheets)
            }
    }

    private fun showPieChart(timesheets: List<Timesheets>) {
        val totalHours = timesheets.sumOf { it.hoursWorked.toInt() }
        val entries = ArrayList<PieEntry>()

        if (totalHours < minHoursGoal) {
            entries.add(PieEntry(minHoursGoal - totalHours.toFloat(), "Below Minimum Goal"))
            entries.add(PieEntry(totalHours.toFloat(), "Hours Worked"))
        } else if (totalHours > maxHoursGoal) {
            entries.add(PieEntry((totalHours - maxHoursGoal).toFloat(), "Above Maximum Goal"))
            entries.add(PieEntry(maxHoursGoal.toFloat(), "Hours Worked"))
        } else {
            entries.add(PieEntry((maxHoursGoal - totalHours).toFloat(), "Within Goal"))
            entries.add(PieEntry(totalHours.toFloat(), "Hours Worked"))
        }

        val dataSet = PieDataSet(entries, "Monthly Hours")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        val data = PieData(dataSet)

        pieChart.data = data
        pieChart.invalidate()
    }
}
