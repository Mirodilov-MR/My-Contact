package com.example.myapplication0000.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts")
    fun getAllContacts(): LiveData<List<Contacts>>
@Insert
suspend fun insert(contact: Contacts)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: Contacts)

    @Delete
    fun delete(contact: Contacts)

    @Update
    fun update(contact: Contacts)

    @Query("SELECT * FROM contacts WHERE number = :number")
    fun getContactByNumber(number: String): Contacts?
}
