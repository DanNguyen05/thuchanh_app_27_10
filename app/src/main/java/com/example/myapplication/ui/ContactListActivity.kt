package com.example.myapplication.ui

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.data.Contact
import com.example.myapplication.data.database.LearningDao

class ContactListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        val contactListView: ListView = findViewById(R.id.contact_list_view)
        val dao = LearningDao(this)
        val contacts: List<Contact> = dao.getAllContacts()

        val adapter = ContactAdapter(this, contacts) { contact ->
            val intent = MessageScreenActivity.newIntent(this, contact.id)
            startActivity(intent)
        }
        contactListView.adapter = adapter
    }
}
