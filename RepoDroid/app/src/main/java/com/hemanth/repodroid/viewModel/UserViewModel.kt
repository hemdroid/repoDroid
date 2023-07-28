package com.hemanth.repodroid.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hemanth.repodroid.data.RepoTable
import com.hemanth.repodroid.model.RepositoryData
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(private val repository: RepositoryClass) : ViewModel() {

    var repoResponse: MutableLiveData<Response<RepositoryData>> = MutableLiveData()

    fun githubRepository(ownerName: String, repoName: String) {
        viewModelScope.launch {
            val response = repository.getRepo(ownerName = ownerName, repositoryName = repoName)
            repoResponse.value = response
        }
    }

    fun insertTheRepo(repotity: RepoTable) = viewModelScope.launch { repository.insertTheRepo(repotity) }

}