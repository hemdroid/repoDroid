package com.hemanth.repodroid.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hemanth.repodroid.data.RepositoryTable

class UserViewModelFactory(repodao: RepositoryTable): ViewModelProvider.Factory {
    private val repository: RepositoryClass = RepositoryClass(repodao)
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(repository) as T
    }
}