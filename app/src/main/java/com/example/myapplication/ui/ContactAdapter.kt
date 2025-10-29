package com.example.myapplication.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.data.Contact

class ContactAdapter(
    private val context: Context,
    private var contacts: List<Contact>,
    private val onContactClick: (Contact) -> Unit
) : BaseAdapter() {

    override fun getCount(): Int = contacts.size

    override fun getItem(position: Int): Any = contacts[position]

    override fun getItemId(position: Int): Long = contacts[position].id

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_contact, parent, false)

        val contact = contacts[position]

        val nameTextView = view.findViewById<TextView>(R.id.contact_name)
        val phoneTextView = view.findViewById<TextView>(R.id.contact_phone)

        nameTextView.text = contact.name
        phoneTextView.text = contact.phone

        view.setOnClickListener {
            onContactClick(contact)
        }

        return view
    }

    fun updateContacts(newContacts: List<Contact>) {
        contacts = newContacts
        notifyDataSetChanged()
    }
}

