package com.example.mangomessenger.ui.registry

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mangomessenger.databinding.FragmentAfterRegistryBinding
import com.example.mangomessenger.ui.login.LoginActivity

class AfterRegistryFragment : Fragment() {
    private var _binding: FragmentAfterRegistryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAfterRegistryBinding.inflate(inflater, container, false)
        binding.signInButton.setOnClickListener {
            val activity = Intent(requireContext(), LoginActivity::class.java)
            startActivity(activity)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = AfterRegistryFragment()
    }
}