package com.example.myapplication0000.fragments.update

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.BuildConfig
import com.example.myapplication0000.viewModel.ContactViewModel
import com.example.myapplication0000.databinding.FragmentEditDataBinding
import com.example.myapplication0000.room.Contacts
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import java.io.IOException
import android.util.Base64
import android.widget.AdapterView
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class EditData : Fragment() {

    private var viewBinding: FragmentEditDataBinding? = null
    private val binding get() = viewBinding!!
    private val viewModel: ContactViewModel by viewModels()
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2
    lateinit var versionNUM: TextView
    private var positionNumberID = 0
    private val countryList = arrayOf(
        "Uzbekistan",
        "Afghanistan",
        "Albania",
        "Algeria",
        "Andorra",
        "Angola",
        "Antigua and Barbuda",
        "Argentina",
        "Armenia",
        "Australia",
        "Austria",
        "Azerbaijan",
        "Bahamas",
        "Bahrain",
        "Bangladesh",
        "Barbados",
        "Belarus",
        "Belgium",
        "Belize",
        "Benin",
        "Bhutan",
        "Bolivia",
        "Bosnia and Herzegovina",
        "Botswana",
        "Brazil",
        "Brunei",
        "Bulgaria",
        "Burkina Faso",
        "Burundi",
        "Cabo Verde",
        "Cambodia",
        "Cameroon",
        "Canada",
        "Central African Republic",
        "Chad",
        "Chile",
        "China",
        "Colombia",
        "Comoros",
        "Congo (Congo-Brazzaville)",
        "Costa Rica",
        "Croatia",
        "Cuba",
        "Cyprus",
        "Czechia (Czech Republic)",
        "Democratic Republic of the Congo (Congo-Kinshasa)",
        "Denmark",
        "Djibouti",
        "Dominica",
        "Dominican Republic",
        "East Timor (Timor-Leste)",
        "Ecuador",
        "Egypt",
        "El Salvador",
        "Equatorial Guinea",
        "Eritrea",
        "Estonia",
        " Eswatini (f.k.a. Swaziland)",
        "Ethiopia",
        "Fiji",
        "Finland",
        "France",
        "Gabon",
        "Gambia",
        "Georgia",
        "Germany",
        "Ghana",
        "Greece",
        "Grenada",
        "Guatemala",
        "Guinea",
        "Guinea-Bissau",
        "Guyana",
        "Haiti",
        "Honduras",
        "Hungary",
        "Iceland",
        "India",
        "Indonesia",
        "Iran",
        "Iraq",
        "Ireland",
        "Israel",
        "Italy",
        "Ivory Coast (Côte d'Ivoire)",
        "Jamaica",
        "Japan",
        "Jordan",
        "Kazakhstan",
        "Kenya",
        "Kiribati",
        "Kuwait",
        "Kyrgyzstan",
        "Laos",
        "Latvia",
        "Lebanon",
        "Lesotho",
        "Liberia",
        "Libya",
        "Liechtenstein",
        "Lithuania",
        "Luxembourg",
        "Madagascar",
        "Malawi",
        "Malaysia",
        "Maldives",
        "Mali",
        "Malta",
        "Marshall Islands",
        "Mauritania",
        "Mauritius",
        "Mexico",
        "Micronesia",
        "Moldova",
        "Monaco",
        "Mongolia",
        "Montenegro",
        "Morocco",
        "Mozambique",
        "Myanmar (Burma)",
        "Namibia",
        "Nauru",
        "Nepal",
        "Netherlands",
        " New Zealand",
        "Nicaragua",
        "Niger",
        "Nigeria",
        "North Korea",
        "North Macedonia",
        "Norway",
        "Oman",
        "Pakistan",
        "Palau",
        "Panama",
        "Papua New Guinea",
        "Paraguay",
        "Peru",
        "Philippines",
        "Poland",
        "Portugal",
        "Qatar",
        "Romania",
        "Russia",
        "Rwanda",
        "Saint Kitts and Nevis",
        "Saint Lucia",
        "Saint Vincent and the Grenadines",
        "Samoa",
        "San Marino",
        "Sao Tome and Principe",
        "Saudi Arabia",
        "Senegal",
        "Serbia",
        "Seychelles",
        "Sierra Leone",
        "Singapore",
        "Slovakia",
        "Slovenia",
        "Solomon Islands",
        "Somalia",
        "South Africa",
        "South Korea",
        "South Sudan",
        "Spain",
        "Sri Lanka",
        "Sudan",
        "Suriname",
        "Sweden",
        "Switzerland",
        "Syria",
        "Taiwan",
        "Tajikistan",
        "Tanzania",
        "Thailand",
        "Togo",
        "Tonga",
        "Trinidad and Tobago",
        "Tunisia",
        "Turkey",
        "Turkmenistan",
        "Tuvalu",
        "Uganda",
        "Ukraine",
        "United Arab Emirates",
        "United Kingdom",
        "United States",
        "Uruguay",
        "Uzbekistan",
        "Vanuatu",
        "Vatican City (Holy See)",
        "Venezuela",
        "Vietnam",
        "Yemen",
        "Zambia",
        "Zimbabwe"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentEditDataBinding.inflate(inflater, container, false)
        val getObject = arguments?.getParcelable<Contacts>("position_id")
        println("getObject =  $getObject")
        binding.addImage.setOnClickListener {
            showPictureDialog()
        }
        versionNUM = binding.versionTV
        val version = "Version " + BuildConfig.VERSION_NAME
        versionNUM.text = version
        positionNumberID = getObject?.id!!
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, countryList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.countrySP.adapter = adapter
        binding.nameTV.setText(getObject.name)
        binding.phoneNumberField.setText(getObject.number)
        binding.addressField.setText(getObject.address)
        binding.passwordField.setText(getObject.password)

        getObject?.imageBase64?.let {
            val decodedImage = decodeBase64Image(it)
            binding.imageView.setImageBitmap(decodedImage)
        }
        val countryPosition = countryList.indexOf(getObject.country)

        if (countryPosition != -1) {
            binding.countrySP.setSelection(countryPosition)
        } else {
            Toast.makeText(requireContext(), "Spinnerda muammo bolmoqda!", Toast.LENGTH_LONG).show()
        }

        // Add an OnItemSelectedListener to the spinner
        binding.countrySP.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // Clear the phone number field whenever a new country is selected
                binding.phoneNumberField.text.clear()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }
        binding.submitButton.setOnClickListener {
            createContact()
        }
        return viewBinding!!.root
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

    private fun createContact() {
        val name = binding.nameTV.text.toString().trim()
        val number = binding.phoneNumberField.text.toString().trim()
        val country = binding.countrySP.selectedItem.toString().trim()
        val address = binding.addressField.text.toString().trim()
        val password = binding.passwordField.text.toString().trim()
        val imageBase64 = viewModel.getImageBase64()
        if (name.isEmpty()) {
            binding.nameTV.error = "Name cannot be empty"
            return
        }
        if (number.isEmpty()) {
            binding.phoneNumberField.error = "Number cannot be empty"
            return
        }
        if (address.isEmpty()) {
            binding.addressField.error = "Address cannot be empty"
            return
        }
        if (password.isEmpty()) {
            binding.passwordField.error = "Password cannot be empty"
            return
        }
        val data = Contacts(positionNumberID, name, number, country, address, password, imageBase64)
        viewModel.updateContacts(data)
        Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()
        findNavController().popBackStack()
    }

    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(requireContext())
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from Gallery", "Capture photo from Camera")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> galleryCheckPermission()
                1 -> cameraCheckPermission()
            }
        }
        pictureDialog.show()
    }

    private fun galleryCheckPermission() {
        val permissionToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        Dexter.withContext(requireContext())
            .withPermission(permissionToRequest)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                    gallery()
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                    Toast.makeText(
                        requireContext(),
                        "You have denied the storage permission to select an image",
                        Toast.LENGTH_SHORT
                    ).show()
                    showRotationalDialogForPermission()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permission: PermissionRequest?,
                    token: PermissionToken?
                ) {
                    showRotationalDialogForPermission()
                }
            }).onSameThread().check()
    }

    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    private fun cameraCheckPermission() {
        val permissionsToRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            arrayOf(
                android.Manifest.permission.READ_MEDIA_IMAGES,
                android.Manifest.permission.CAMERA
            )
        } else {
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            )
        }

        Dexter.withContext(requireContext())
            .withPermissions(*permissionsToRequest)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            camera()
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: MutableList<PermissionRequest>?,
                    token: PermissionToken?
                ) {
                    showRotationalDialogForPermission()
                }
            }).onSameThread().check()
    }

    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap?
                    bitmap?.let {
                        val circleBitmap = getCircularBitmap(it)
                        binding.imageView.setImageBitmap(circleBitmap)
                        val imageBase64 = bitmapToBase64(circleBitmap)
                        viewModel.setImageBase64(imageBase64)
                    }
                }

                GALLERY_REQUEST_CODE -> {
                    val imageUri = data?.data
                    imageUri?.let {
                        val bitmap = getBitmapFromUri(it)
                        bitmap?.let {
                            val circleBitmap = getCircularBitmap(it)
                            binding.imageView.setImageBitmap(circleBitmap)
                            // Convert bitmap to base64
                            val imageBase64 = bitmapToBase64(circleBitmap)
                            // Assign base64 to the Contacts object
                            viewModel.setImageBase64(imageBase64)
                        }
                    }
                }
            }
        }
    }

    private fun bitmapToBase64(bitmap: Bitmap): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun getCircularBitmap(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val outputBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)
        val paint = Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }
        val radius = width.coerceAtMost(height) / 2f
        canvas.drawCircle(width / 2f, height / 2f, radius, paint)
        return outputBitmap
    }

    private fun getBitmapFromUri(uri: Uri): Bitmap? {
        return try {
            val inputStream = requireContext().contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun showRotationalDialogForPermission() {
        AlertDialog.Builder(requireContext())
            .setMessage(
                "It looks like you have turned off permissions"
                        + " required for this feature. It can be enabled under App settings!!!"
            )
            .setPositiveButton("Go To SETTINGS") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireActivity().packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}
