package com.hemanth.repodroid.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepositoryTable {
    @Query("SELECT * FROM 'RepoTable'")
    fun getAllRepo():List<RepoTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepo(repotity: RepoTable)


}