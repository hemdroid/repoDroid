package com.hemanth.repodroid.viewModel

import com.hemanth.repodroid.data.RepoTable
import com.hemanth.repodroid.data.RepositoryTable
import com.hemanth.repodroid.model.RepositoryData

import com.hemanth.repodroid.sockets.ApiClient
import retrofit2.Response

class RepositoryClass(private val repositoryTable : RepositoryTable) {

    suspend fun getRepo(
        ownerName: String,
        repositoryName: String
    ): Response<RepositoryData> {
        return ApiClient.api.getRepository(ownerName, repositoryName)
    }

    suspend fun insertTheRepo(repotity: RepoTable){
        repositoryTable.insertRepo(repotity)
    }

}