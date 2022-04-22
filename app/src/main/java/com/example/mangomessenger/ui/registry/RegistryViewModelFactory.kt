package com.example.mangomessenger.ui.registry

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RegistryViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.newInstance()
    }
}