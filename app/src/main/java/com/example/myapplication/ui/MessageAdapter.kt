package com.example.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.data.Message
import java.text.SimpleDateFormat
import java.util.*

class MessageAdapter(
    private val context: Context,
    private var messages: List<Message>
) : BaseAdapter() {

    private val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    override fun getCount(): Int = messages.size

    override fun getItem(position: Int): Any = messages[position]

    override fun getItemId(position: Int): Long = messages[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val message = messages[position]
        val layoutId = if (message.isSentByMe) {
            R.layout.item_message_sent
        } else {
            R.layout.item_message_received
        }

        val view = convertView ?: LayoutInflater.from(context).inflate(layoutId, parent, false)

        val contentTextView = view.findViewById<TextView>(R.id.message_content)
        val timeTextView = view.findViewById<TextView>(R.id.message_time)

        contentTextView.text = message.content
        timeTextView.text = dateFormat.format(Date(message.timestamp))

        return view
    }

    fun updateMessages(newMessages: List<Message>) {
        messages = newMessages
        notifyDataSetChanged()
    }
}

