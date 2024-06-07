package com.example.opscpart3

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private lateinit var imageView : ImageView
    private lateinit var title : TextView
    private lateinit var abbr: EditText
    private lateinit var reg: TextView
    private lateinit var EmailAddress: EditText
    private lateinit var email :TextView
    private lateinit var passwordtxt: TextView
    private lateinit var Password: EditText
    private lateinit var confirmtxt: TextView
    private lateinit var ConfirmPassword: EditText
    private lateinit var checkbox: CheckBox
    private lateinit var buttonemail: Button
    private lateinit var textView2: TextView
    private lateinit var usernametxt: TextView
    private lateinit var Username: EditText
    private lateinit var auth:FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth = FirebaseAuth.getInstance()
        title = findViewById(R.id.title)
        abbr = findViewById(R.id.abbrL)
        reg = findViewById(R.id.reg)
        EmailAddress = findViewById(R.id.EmailAddress)
        email = findViewById(R.id.email)
        passwordtxt = findViewById(R.id.passwordtxt)
        Password = findViewById(R.id.Password)
        confirmtxt = findViewById(R.id.confirmtxt)
        ConfirmPassword = findViewById(R.id.ConfirmPassword)
        checkbox = findViewById(R.id.checkbox)
        buttonemail = findViewById(R.id.buttonemail)
        textView2 = findViewById(R.id.textView2)
        usernametxt = findViewById(R.id.usernametxt)
        Username = findViewById(R.id.Username)


        checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                Toast.makeText(this, "Answer was submitted", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Please tick the checkbox", Toast.LENGTH_SHORT).show()
            }
        }

        textView2.setOnClickListener {
            startActivity(Intent(this,Login::class.java))
            finish()

        }

        buttonemail.setOnClickListener {
            val check = checkbox.text.toString().trim()
            val username = Username.text.toString().trim()
            val password = Password.text.toString().trim()
            val confirm = ConfirmPassword.text.toString().trim()
            val email = EmailAddress.text.toString().trim()
            if(check.isNotEmpty()&& username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirm.isNotEmpty()) {
                if(password == confirm) {
                    registerUser(email, password)
                    Toast.makeText(this, "User created successfully", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this,"Password doesn't match", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(this, "Please enter your email, password, and confirm your email", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){task ->
                if(task.isSuccessful) {
                    startActivity(Intent(this, Login::class.java))
                    finish()
                }else{
                    Toast.makeText(this, "Registration failed.${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }


            }

    }
    }