package com.example.opscpart3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class GoalSetting : AppCompatActivity() {

    private lateinit var minGoalEditText: EditText
    private lateinit var maxGoalEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_setting)

        minGoalEditText = findViewById(R.id.minGoalEditText)
        maxGoalEditText = findViewById(R.id.maxGoalEditText)
        saveButton = findViewById(R.id.saveButton)

        firestore = FirebaseFirestore.getInstance()

        saveButton.setOnClickListener {
            saveGoalsToFirestore()
        }
    }

    private fun saveGoalsToFirestore() {
        val minGoal = minGoalEditText.text.toString().toIntOrNull()
        val maxGoal = maxGoalEditText.text.toString().toIntOrNull()

        if (minGoal == null || maxGoal == null) {
            showToast("Please enter valid numbers")
            return
        }

        val goals = hashMapOf(
            "minGoal" to minGoal,
            "maxGoal" to maxGoal
        )

        firestore.collection("Goals")
            .document("user_goals")
            .set(goals)
            .addOnSuccessListener {
                showToast("Goals saved successfully")
                finish()
            }
            .addOnFailureListener { e ->
                showToast("Failed to save goals: ${e.message}")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
