package com.hemanth.repodroid.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RepoTable")
data class RepoTable(
    @PrimaryKey @ColumnInfo(name = "Repository Name")
    val repositoryName:String,
    @ColumnInfo(name = "Description")
    val descriptionRepo:String,
    @ColumnInfo(name = "HTML Url")
    val htmlUrl:String,
    @ColumnInfo(name = "Owner")
    val ownerName:String
)