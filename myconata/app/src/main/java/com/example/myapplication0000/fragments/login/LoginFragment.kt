package com.example.myapplication0000.fragments.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication0000.BuildConfig
import com.example.myapplication0000.viewModel.ContactViewModel
import com.example.myapplication0000.R
import com.example.myapplication0000.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ContactViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        val version = "Version " + BuildConfig.VERSION_NAME
        binding.versionTV.text = version

        binding.SignUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_createContact)
        }
        binding.submitButton.setOnClickListener {
            val enteredNumber = binding.phoneNumberField.text.toString()
            val enteredPassword = binding.passwordField.text.toString()
            val contact = viewModel.getContactByNumber(enteredNumber)

            if (contact != null) {
                if (contact.password == enteredPassword) {

                    findNavController().navigate(R.id.contactList)
                } else {
                    Toast.makeText(requireContext(), "Parol xato", Toast.LENGTH_SHORT).show()
                }
            } else {

                Toast.makeText(requireContext(), "Raqam xato", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
