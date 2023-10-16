package com.example.myapplication0000.room

import ContactBottomSheetDialogFragment
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication0000.R
import com.example.myapplication0000.databinding.ContactsLayoutBinding
import android.util.Base64
import java.io.ByteArrayInputStream
import java.io.IOException

class ContactsAdapter(val context: Context, val list: List<Contacts>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {
    lateinit var listener: OnUserClickedListener

    class ViewHolder(val binding: ContactsLayoutBinding) : RecyclerView.ViewHolder(binding.root)
    private val dao = ContactDatabase.getDatabaseInstance(context).contactsDao()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContactsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = list[position]
        holder.binding.nameTV.text = contact.name
        holder.binding.phoneNumberField.text = contact.number
        contact.imageBase64?.let {
            val decodedImage = decodeBase64Image(it)
            holder.binding.imageView.setImageBitmap(decodedImage)
        }
        holder.itemView.setOnClickListener {
            val bottomSheetDialogFragment = ContactBottomSheetDialogFragment(contact)
            bottomSheetDialogFragment.show((context as AppCompatActivity).supportFragmentManager, bottomSheetDialogFragment.tag)
        }
        holder.binding.dots.setOnClickListener {
            val popupMenu = PopupMenu(context, holder.binding.dots)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_contact -> {
                        editContact(contact, position)
                        val bundle = Bundle()
                        bundle.putParcelable("position_id", contact)
                        holder.itemView.findNavController()
                            .navigate(R.id.action_contactList_to_editData, bundle)
                        true
                    }
                    R.id.delete_contact -> {
                        deleteContact(contact, position)
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }
    }
fun decodeBase64Image(base64Image: String): Bitmap? {
    val imageBytes = Base64.decode(base64Image, Base64.DEFAULT)
    val inputStream = ByteArrayInputStream(imageBytes)
    return try {
        BitmapFactory.decodeStream(inputStream)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}
    private fun deleteContact(contact: Contacts, position: Int) {
        dao.delete(contact)
        notifyItemRemoved(position)
    }
    private fun editContact(contact: Contacts, position: Int) {
        dao.update(contact)
    }
    override fun getItemCount(): Int {
        return list.size
    }
    fun setOnUserClickedListener(listener: OnUserClickedListener) {
        this.listener = listener
    }
    interface OnUserClickedListener {
        fun onUserClicked(position: Int)
    }
}