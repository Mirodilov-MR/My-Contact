package com.example.myapplication0000.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication0000.repository.ContactRepository
import com.example.myapplication0000.room.ContactDatabase
import com.example.myapplication0000.room.Contacts
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ContactRepository
    init {
        val dao = ContactDatabase.getDatabaseInstance(application).contactsDao()
        repository = ContactRepository(dao)
    }
    fun getContactByNumber(number: String): Contacts? {
        return repository.getContactByNumber(number)
    }
    fun addContacts(contact: Contacts) {
        viewModelScope.launch {
            repository.insertContact(contact)
        }
    }
    fun updateContacts(contact: Contacts) {
        viewModelScope.launch {
            repository.updateContact(contact)
        }
    }
    fun getAllContacts(): LiveData<List<Contacts>> = repository.getAllContacts()
    fun isNumberUsed(number: String): Boolean {
        return repository.isNumberUsed(number)
    }
    private var imageBase64: String? = null
    fun setImageBase64(base64: String) {
        imageBase64 = base64
    }
    fun getImageBase64(): String? {
        return imageBase64
    }
}
