package com.example.myapplication.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.data.Message
import com.example.myapplication.data.database.LearningDao

class MessageScreenActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_CONTACT_ID = "contact_id"

        fun newIntent(context: Context, contactId: Long): Intent {
            return Intent(context, MessageScreenActivity::class.java).apply {
                putExtra(EXTRA_CONTACT_ID, contactId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_screen)

        val contactId = intent.getLongExtra(EXTRA_CONTACT_ID, -1)
        val dao = LearningDao(this)

        val messageListView: ListView = findViewById(R.id.message_list_view)
        val messageInput: EditText = findViewById(R.id.message_input)
        val sendButton: Button = findViewById(R.id.send_button)

        val messages: List<Message> = dao.getMessagesByContactId(contactId)
        val adapter = MessageAdapter(this, messages)
        messageListView.adapter = adapter

        sendButton.setOnClickListener {
            val content = messageInput.text.toString()
            if (content.isNotEmpty()) {
                val message = Message(
                    id = 0,
                    contactId = contactId,
                    content = content,
                    timestamp = System.currentTimeMillis(),
                    isSentByMe = true
                )
                dao.addMessage(message)
                messageInput.text.clear()

                val updatedMessages = dao.getMessagesByContactId(contactId)
                adapter.updateMessages(updatedMessages)
            }
        }
    }
}
