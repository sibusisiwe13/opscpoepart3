package com.example.opscpart3

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var imageView3 : ImageView
    private lateinit var abbrL : EditText
    private lateinit var titleL : TextView
    private lateinit var login : TextView
    private lateinit var usernametxt : TextView
    private lateinit var passwordtxt : TextView
    private lateinit var Password : EditText
    private lateinit var Username : EditText
    private lateinit var button3 : Button
    private lateinit var auth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()
        imageView3 = findViewById(R.id.imageView3)
        abbrL = findViewById(R.id.abbrL)
        titleL = findViewById(R.id.titleL)
        login = findViewById(R.id.login)
        usernametxt = findViewById(R.id.usernametxt)
        passwordtxt = findViewById(R.id.passwordtxt)
        Password = findViewById(R.id.Password)
        Username = findViewById(R.id.Username)
        button3 = findViewById(R.id.button3)

        button3.setOnClickListener {
            val username = Username.text.toString().trim()
            val password = Password.text.toString().trim()
            if(username.isNotEmpty() && password.isNotEmpty()) {
                Toast.makeText(this, "Submitted Successfully",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Please fill in your username and password correctly", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
