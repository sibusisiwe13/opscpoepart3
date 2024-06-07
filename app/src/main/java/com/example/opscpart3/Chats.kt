package com.example.opscpart3

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Chats : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messages: RelativeLayout
    private lateinit var chatstxt: EditText
    private lateinit var sendbutton: Button
    private lateinit var Messages:MutableList<Messages>
    private lateinit var MessageAdapter: MessageAdapter
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chats)
        auth = FirebaseAuth.getInstance()
        messages = findViewById(R.id.messageLayout)
        chatstxt = findViewById(R.id.chatstxt)
        sendbutton = findViewById(R.id.sendButton)
        MessageAdapter = MessageAdapter(Messages)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter = MessageAdapter
        Messages= mutableListOf()
        database = FirebaseDatabase.getInstance().reference.child("chats")
        sendbutton.setOnClickListener {
            sendText()
        }
        listenforText()






    }

    private fun listenforText() {
        database.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val Messages = snapshot.getValue(Messages::class.java)
                Messages?.let {
                    Messages.add(it)
                    MessageAdapter.notifyItemInserted(Messages.size-1)
                    chatRecyclerView.scrollToPosition(Messages.size-1)
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun sendText() {
        val messages = chatstxt.text.toString().trim()
        if (messages.isNotEmpty()){
            val Messages = Messages(
                messages = messages,
                sender = FirebaseAuth.getInstance().currentUser?.uid?:"",
                timestamp = System.currentTimeMillis()
            )
            database.push().setValue(Messages)
            chatstxt.text.clear()
        }
    }


}

private fun <E> MutableList<E>?.add(element: MutableList<E>) {
    TODO("Not yet implemented")
}
