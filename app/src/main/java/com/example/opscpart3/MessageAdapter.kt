package com.example.opscpart3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.opscpart3.MessageAdapter.*

class MessageAdapter (private val Messages: List<Messages>):
        RecyclerView.Adapter<ChatsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chats, parent, false)
        return ChatsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatsViewHolder, position: Int) {
        val messages = Messages[position]
        holder.bind(messages)

    }

    override fun getItemCount() = Messages.size
    class ChatsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val messageTextView: TextView = itemView.findViewById(R.id.textViewMessage)

        fun bind (messages: Messages){
            messageTextView.text = messages.messages
        }
    }
}


