package com.example.mangomessenger.ui.registry

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.mangomessenger.R
import com.example.mangomessenger.ui.login.LoginActivity

class AfterRegistryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.after_registry_fragment, container, false)
        view.findViewById<Button>(R.id.signInButton).setOnClickListener {
            val activity = Intent(requireContext(), LoginActivity::class.java)
            startActivity(activity)
        }
        return view
    }

    companion object {
        fun newInstance() = AfterRegistryFragment()
    }
}