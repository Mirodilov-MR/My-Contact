package com.example.myapplication0000.room

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "Contacts")
data class Contacts(
    @PrimaryKey(autoGenerate = true)
    var id: Int?=null,
    var name: String,
    var number: String,
    var country: String,
    var address: String,
    var password: String,
    var imageBase64: String? = null
) : Parcelable