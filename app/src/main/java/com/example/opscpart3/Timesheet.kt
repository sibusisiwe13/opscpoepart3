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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

private val PICK_IMAGE_REQUEST = 100
private var filePath: Uri? = null
class Timesheet : AppCompatActivity() {

    private lateinit var date: EditText
    private lateinit var endTime: EditText
    private lateinit var startTime: EditText
    private lateinit var description: EditText
    private lateinit var dropdown: Spinner
    private lateinit var create: Button
    private lateinit var delete: Button
    private lateinit var photo: ImageView
    private lateinit var addimage: Button
    private lateinit var sms: Button
    private lateinit var storage: FirebaseStorage
    private lateinit var db:FirebaseFirestore
    private lateinit var selectedCategory: EditText



    override fun onActivityResult(requestCode:Int, resultCode: Int, data: Intent?){
        super.onActivityResult(resultCode, requestCode, data )
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data !=null && data.data!= null){
            filePath = data.data
            uploadImage()
        }
        addimage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action= Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Choose preferable image"), PICK_IMAGE_REQUEST)
        }
    }






    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.timesheet)

        db = FirebaseFirestore.getInstance()
        storage= FirebaseStorage.getInstance()
        date = findViewById(R.id.date)
        endTime = findViewById(R.id.endTime)
        startTime = findViewById(R.id.startTime)
        description = findViewById(R.id.description)
        photo = findViewById(R.id.photo)
        addimage = findViewById(R.id.addimage)
        sms = findViewById(R.id.logo)
        selectedCategory = findViewById(R.id.SelectedCategory)
        create = findViewById(R.id.create)
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
            startActivity(Intent(this, TimesheetList::class.java))
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



    }
    private fun uploadImage() {
        if (filePath != null) {
            val ref = storage.reference.child("images/" + UUID.randomUUID().toString())
            ref.putFile(filePath!!)
                .addOnSuccessListener {}
            ref.downloadUrl.addOnSuccessListener { uri ->
                saveTask(uri.toString())
            }
        }
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

    private fun saveTask(photoUrl: String? = null) {
        val date = date.text.toString()
        val endTime = endTime.text.toString()
        val startTime = startTime.text.toString()
        val description = description.text.toString()
        val categories = dropdown.selectedItem.toString()
val Timesheet = hashMapOf(
    "date" to date,
    "endTime" to endTime,
    "startTime" to startTime,
    "description" to description,
    "categories" to categories,
    "photoUrl" to photoUrl
)
        db.collection("Timesheets").add(Timesheet)
            .addOnSuccessListener { documentReference -> }

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


