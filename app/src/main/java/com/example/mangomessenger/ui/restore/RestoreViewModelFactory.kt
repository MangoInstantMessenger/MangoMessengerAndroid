package com.example.mangomessenger.ui.restore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class RestoreViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.newInstance()
    }
}