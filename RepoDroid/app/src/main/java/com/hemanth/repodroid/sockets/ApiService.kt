package com.hemanth.repodroid.sockets

import com.hemanth.repodroid.model.RepositoryData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("{owner}/{repo}")
    suspend fun getRepository(
        @Path("owner") ownerName:String,
        @Path("repo") repoName:String
    ): Response<RepositoryData>
}