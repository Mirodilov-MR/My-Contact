package com.example.myapplication0000.fragments.userList
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication0000.viewModel.ContactViewModel
import com.example.myapplication0000.R
import com.example.myapplication0000.databinding.FragmentContactListBinding
import com.example.myapplication0000.room.ContactsAdapter

class ContactListFragment : Fragment(R.layout.fragment_contact_list),
    ContactsAdapter.OnUserClickedListener {
    private lateinit var binding: FragmentContactListBinding
    private val viewModel: ContactViewModel by viewModels()
    private lateinit var rvAdapter: ContactsAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContactListBinding.bind(view)

        viewModel.getAllContacts().observe(viewLifecycleOwner, Observer { list ->
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.adapter = ContactsAdapter(requireContext(), list)
            rvAdapter = ContactsAdapter(context!!, list)
            binding?.recyclerView?.adapter = rvAdapter
            rvAdapter.setOnUserClickedListener(this)
        })
    }
    override fun onUserClicked(position: Int) {
        Toast.makeText(context, "bosildi  - $position - ", Toast.LENGTH_SHORT).show()
    }
}