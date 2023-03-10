package com.example.convidados.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.convidados.viewmodel.AllGuestsViewModel
import com.example.convidados.databinding.FragmentAllGuestsBinding
import com.example.convidados.view.adapter.GuestAdapter

class AllGuestsFragment : Fragment() {

    private var _binding: FragmentAllGuestsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: AllGuestsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, bundle: Bundle?): View {
        viewModel = ViewModelProvider(this)[AllGuestsViewModel::class.java]
        _binding = FragmentAllGuestsBinding.inflate(inflater, container, false)

        // Layout RecyclerView
        binding.recyclerAllGuests.layoutManager = LinearLayoutManager(context)

        // Adapter
        binding.recyclerAllGuests.adapter = GuestAdapter()

        viewModel.getPresece()

        observe()
        return binding.root
    }

    private fun observe() {
        viewModel.guests.observe(viewLifecycleOwner) {
            val a = ""
            //Lista de convidados
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}