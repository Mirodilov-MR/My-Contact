package com.example.myapplication0000.fragments
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.R

class SmsFragment : Fragment() {
    lateinit var message: EditText
    lateinit var phone: EditText
    lateinit var send: Button
    var userMessage: String = ""
    var userPhone: String = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sms, container, false)
        message = view.findViewById(R.id.editTextMessage)
        phone = view.findViewById(R.id.editTextPhone)
        send = view.findViewById(R.id.buttonSend)
        send.setOnClickListener {
            userMessage = message.text.toString()
            userPhone = phone.text.toString()
            sendSMS(userMessage, userPhone)
            findNavController().popBackStack()
        }
        return view
    }
    private fun sendSMS(userMsg: String, userPhone: String) {
        if (checkSelfPermission(
                requireContext(),
                android.Manifest.permission.SEND_SMS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // You can use the API that requires the permission.
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.SEND_SMS),
                1
            )
        } else {
            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT >= 23) {
                smsManager = requireContext().getSystemService(SmsManager::class.java)
            } else {
                smsManager = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(userPhone, null, userMsg, null, null)
            Toast.makeText(requireContext(), "Message sent", Toast.LENGTH_LONG).show()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val smsManager: SmsManager
            if (Build.VERSION.SDK_INT >= 23) {
                smsManager = requireContext().getSystemService(SmsManager::class.java)
            } else {
                smsManager = SmsManager.getDefault()
            }
            smsManager.sendTextMessage(userPhone, null, userMessage, null, null)
            Toast.makeText(requireContext(), "Message sent", Toast.LENGTH_LONG).show()
        }
    }
}
