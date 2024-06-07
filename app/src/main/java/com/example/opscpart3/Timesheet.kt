package com.example.opscpart3

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Timesheet : AppCompatActivity() {

    private lateinit var date: EditText
    private lateinit var endTime: EditText
    private lateinit var startTime: EditText
    private lateinit var description: EditText
    private lateinit var dropdown: Spinner
    private lateinit var create: Button
    private lateinit var edit: Button
    private lateinit var delete: Button
    private lateinit var photo: ImageView
    private lateinit var addimage: Button
    private lateinit var sms: Button
    private lateinit var selectedCategory: EditText
    private lateinit var auth: FirebaseAuth

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK && data != null) {
            val imageUri: Uri? = data.data
            photo.setImageURI(imageUri)
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timesheet)

        auth = FirebaseAuth.getInstance()
        date = findViewById(R.id.date)
        endTime = findViewById(R.id.endTime)
        startTime = findViewById(R.id.startTime)
        description = findViewById(R.id.description)
        photo = findViewById(R.id.photo)
        addimage = findViewById(R.id.addimage)
        sms = findViewById(R.id.logo)
        selectedCategory = findViewById(R.id.SelectedCategory)
        create = findViewById(R.id.create)
        edit = findViewById(R.id.edit)
        delete = findViewById(R.id.delete)
        dropdown = findViewById(R.id.dropdown)
        fetchCategories()


        val adapter = ArrayAdapter.createFromResource(
            this, R.array.dropdown_options,
            android.R.layout.simple_spinner_dropdown_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = parent?.getItemAtPosition(position).toString()
                this@Timesheet.selectedCategory.setText(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                showToast("No category selected")
            }}

        create.setOnClickListener {
            startActivity(Intent(this, Task::class.java))
            saveTask()
        }

        delete.setOnClickListener {
            val taskId = "your-task-id"
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setMessage("Do you want to delete your Task?")
            alertDialog.setPositiveButton("YES") { _, _ ->
                deleteTask(taskId)
            }
            alertDialog.setNegativeButton("No") { dialog, which ->
                showToast("Task deletion canceled")
            }
            alertDialog.show()
        }

        edit.setOnClickListener {
            editTask()
        }

        addimage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 200)
        }
    }

    private fun editTask() {
        val date = intent.getStringExtra("date")
        val endTime = intent.getStringExtra("endTime")
        val startTime = intent.getStringExtra("startTime")
        val description = intent.getStringExtra("description")
        val categories =intent.getStringExtra("categories")

    }

    private fun deleteTask(taskId: String) {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setMessage("Do you want to delete this task?")
            setPositiveButton("Yes") { dialog, which ->
                deleteTaskFromDatabase(taskId)
            }
            setNegativeButton("No") { dialog, which ->
                showToast("Task deletion canceled")
            }
            show()
        }
    }

    private fun deleteTaskFromDatabase(taskId: String) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("tasks").child(taskId)
        databaseReference.removeValue()
            .addOnSuccessListener {
                showToast("Task deleted successfully")
            }
            .addOnFailureListener { exception ->
                showToast("Failed to delete task: ${exception.message}")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveTask() {
        val date = date.text.toString()
        val endTime = endTime.text.toString()
        val startTime = startTime.text.toString()
        val description = description.text.toString()
        val categories = dropdown.selectedItem.toString()


        TODO("Not yet implemented")

    }

    private fun fetchCategories() {
        val categories = listOf(
            "TASK 1: StartTime-08:00am,EndTime-16:00pm",
            "TASK 2: StartTime-10:00am, EndTime-14:00pm",
            "TASK 3: StartTime-10:30am, EndTime-14:30pm",
            "TASK 4: StartTime-11:00am, EndTime-14:00pm",
            "TASK 5: StartTime-12:00am, EndTime-15:00pm",
            "TASK 6: StartTime-13:00am, EndTime-18:00pm"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdown.adapter = adapter
    }
}