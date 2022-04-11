package com.example.mangomessenger.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import mangomessenger.bisunesslogic.services.SignInService

class SignInViewModelFactory(private val signInService: SignInService) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(SignInService::class.java)
            .newInstance(signInService)
    }
}