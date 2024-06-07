package com.example.opscpart3

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {
    private lateinit var MinGoals: EditText
    private lateinit var MaxGoals: EditText
    private lateinit var Language: Spinner
    private lateinit var Mode: Switch
    private lateinit var ChangeUsername: EditText
    private lateinit var ChangePassword: EditText
    private lateinit var savebtn: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.opscpart3.R.layout.settings)
         MinGoals = findViewById(com.example.opscpart3.R.id.MinGoals)
         MaxGoals = findViewById(com.example.opscpart3.R.id.MaxGoals)
         Language = findViewById(com.example.opscpart3.R.id.Language)
        Mode = findViewById(com.example.opscpart3.R.id.Mode)
        ChangePassword = findViewById(com.example.opscpart3.R.id.ChangePassword)
        ChangeUsername= findViewById(com.example.opscpart3.R.id.ChangeUsername)
        savebtn = findViewById(com.example.opscpart3.R.id.Savebtn)

        val Languages = arrayOf("English", "Isizulu", "French", "Spanish")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, Languages)
        Language.adapter = adapter

        SetSettings()

        savebtn.setOnClickListener {
            val MinGoals = MinGoals.text.toString().toInt()
            val MaxGoals = MaxGoals.text.toString().toInt()
            val ChangeUsername = ChangeUsername.text.toString()
            val ChangePassword = ChangePassword.text.toString()
           val Language = Language.selectedItem.toString()
            val Mode = Mode.isChecked

            saveGoals(MinGoals, MaxGoals)


            save(ChangeUsername,ChangePassword, Language, Mode)
            Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
        }




    }

    private fun saveGoals(minGoals: Int, maxGoals: Int) {
        val sharedPreferences = getSharedPreferences("My Goals", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("MinGoal", minGoals)
        editor.putInt("MaxGoal", maxGoals)
        editor.apply()

    }

    private fun SetSettings() {

        TODO("Not yet implemented")
    }

    private fun save(changeUsername: String, changePassword: String, language: String, mode: Boolean) {
        val sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString ("username",changeUsername)
        editor.putString("password", changePassword)
        editor.putString("language", language)
        editor.putBoolean("Mode", mode)
        editor.apply()

    }

}

