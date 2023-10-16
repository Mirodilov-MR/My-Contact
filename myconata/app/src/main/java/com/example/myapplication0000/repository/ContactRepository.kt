package com.example.myapplication0000.repository

import androidx.lifecycle.LiveData
import com.example.myapplication0000.room.ContactDao
import com.example.myapplication0000.room.Contacts

class ContactRepository(private val dao: ContactDao) {
val users = dao.getAllContacts()
    suspend fun insert(user:Contacts){
        return dao.insert(user)
    }
    fun getAllContacts(): LiveData<List<Contacts>> {
        return dao.getAllContacts()
    }

    fun insertContact(contact: Contacts) {
        dao.insertContact(contact)
    }

    fun updateContact(contact: Contacts) {
        dao.update(contact)
    }

    fun deleteContact(contact: Contacts) {
        dao.delete(contact)
    }

    fun getContactByNumber(number: String): Contacts? {
        return dao.getContactByNumber(number)
    }

    fun isNumberUsed(number: String): Boolean {
        return getContactByNumber(number) != null
    }
}
