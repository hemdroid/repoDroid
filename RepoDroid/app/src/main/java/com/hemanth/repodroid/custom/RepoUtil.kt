package com.hemanth.repodroid.custom

import androidx.recyclerview.widget.DiffUtil
import com.hemanth.repodroid.data.RepoTable

class RepoUtil(private val oldList: List<RepoTable>, private val newList: List<RepoTable>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].repositoryName == newList[newItemPosition].repositoryName
                && oldList[oldItemPosition].descriptionRepo == newList[newItemPosition].descriptionRepo
                && oldList[oldItemPosition].htmlUrl == newList[newItemPosition].htmlUrl
                && oldList[oldItemPosition].ownerName == newList[newItemPosition].ownerName
    }
}