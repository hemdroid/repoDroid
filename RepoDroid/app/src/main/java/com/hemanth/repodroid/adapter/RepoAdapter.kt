package com.hemanth.repodroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hemanth.repodroid.R
import com.hemanth.repodroid.custom.RepoUtil
import com.hemanth.repodroid.data.RepoTable

class RepoAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    private var repoList = emptyList<RepoTable>()

    inner class RepoViewHolder(itemView: View, onItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                onItemClickListener.onClick(adapterPosition)
            }
            itemView.findViewById<ImageView>(R.id.share_btn).setOnClickListener {
                onItemClickListener.onShareButtonClick(adapterPosition)
            }
        }

        fun bind(repotity: RepoTable) {
            itemView.findViewById<TextView>(R.id.repo_title).text = repotity.repositoryName
            itemView.findViewById<TextView>(R.id.repo_description).text = repotity.descriptionRepo
            itemView.findViewById<TextView>(R.id.ownerName).text = repotity.ownerName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoAdapter.RepoViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.single_repo_item, parent, false)
        return RepoViewHolder(itemView, onItemClickListener)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repoList[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = repoList.size

    fun setData(repotity: List<RepoTable>) {
        val repoDiffUtil = RepoUtil(repoList, repotity)
        val repoUtilResult = DiffUtil.calculateDiff(repoDiffUtil)
        this.repoList = repotity
        repoUtilResult.dispatchUpdatesTo(this)
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
        fun onShareButtonClick(position: Int)
    }

}