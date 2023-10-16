import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.R
import com.example.myapplication0000.room.Contacts
import android.graphics.BitmapFactory
import android.util.Base64

class ContactBottomSheetDialogFragment(private val contact: Contacts) : DialogFragment() {
    private companion object {
        private const val CALL_PERMISSION_CODE = 101
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottomsheetlayout, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageView = view.findViewById<ImageView>(R.id.imageView2)
        val call = view.findViewById<ConstraintLayout>(R.id.call_btn)
        val message = view.findViewById<ConstraintLayout>(R.id.message_btn)
        val name = view.findViewById<TextView>(R.id.name_TV)
        val country = view.findViewById<TextView>(R.id.address_field)
        val imageBase64 = contact.imageBase64
        if (!imageBase64.isNullOrEmpty()) {
            val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
            val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
            imageView.setImageBitmap(imageBitmap)
        }
        name.text = contact.name
        country.text = contact.address
        call.setOnClickListener {
            dismiss()
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CALL_PHONE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                makeCall(contact.number)
            } else {
                requestCallPermission()
            }
        }
        message.setOnClickListener {
            dismiss()
            findNavController().navigate(R.id.smsFragment)
            Toast.makeText(requireContext(), "Xabar jo'natish bosildi", Toast.LENGTH_SHORT).show()
        }
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.window?.setGravity(Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL)
    }
    private fun makeCall(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
    private fun requestCallPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.CALL_PHONE),
            CALL_PERMISSION_CODE
        )
    }
}
