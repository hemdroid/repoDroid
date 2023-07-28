package com.hemanth.repodroid.ui

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hemanth.repodroid.R
import com.hemanth.repodroid.adapter.RepoAdapter
import com.hemanth.repodroid.data.RepoDatabase
import com.hemanth.repodroid.data.RepoTable
import com.hemanth.repodroid.data.RepositoryTable
import com.hemanth.repodroid.databinding.FragmentHomeBinding
import com.hemanth.repodroid.viewModel.UserViewModel
import com.hemanth.repodroid.viewModel.UserViewModelFactory
import kotlinx.coroutines.launch


class HomeFragment : Fragment(), RepoAdapter.OnItemClickListener {

    private lateinit var binding: FragmentHomeBinding
    private val repoAdapter: RepoAdapter by lazy { RepoAdapter(this) }
    private var viewModel: UserViewModel? = null
    private lateinit var repodao: RepositoryTable
    private lateinit var repoList: List<RepoTable>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        repodao = RepoDatabase.getDatabase(requireContext()).repoDao()
        val factory = UserViewModelFactory(repodao)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        repoList = repodao.getAllRepo()
        lifecycleScope.launch {
            if (repoList.isEmpty()) {
                binding.trackRepoTv.visibility = View.VISIBLE
            } else {
                binding.trackRepoTv.visibility = View.GONE
                repoAdapter.setData(repoList)
                val layoutManager = LinearLayoutManager(requireContext())
                binding.repoRecyclerView.layoutManager = layoutManager
                binding.repoRecyclerView.adapter = repoAdapter
                layoutManager.stackFromEnd = true
                layoutManager.reverseLayout = true
            }

            binding.addRepoButton.setOnClickListener {
                //Toast.makeText(context, "Hey Buddy!!!!!!", Toast.LENGTH_SHORT).show()
                replaceFragment(AddFragment())
            }
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.registerFragmentContainerView, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onClick(position: Int) {
        openBrowser(position)
    }

    override fun onShareButtonClick(position: Int) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Check this amazing repository of ${repoList[position].htmlUrl} by ${repoList[position].ownerName}"
        )
        startActivity(Intent.createChooser(intent, "Choose the App"))
    }

    private fun openBrowser(position: Int) {
        MaterialAlertDialogBuilder(
            requireContext(),
            R.style.ThemeOverlay_App_MaterialAlertDialog
        )
            .setBackground(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.rect,
                    requireContext().theme
                )
            )
            .setTitle(resources.getString(R.string.openInBrowser))
            .setMessage("Do you want to open ${repoList[position].repositoryName} by ${repoList[position].ownerName}")
            .setIcon(R.drawable.baseline_open_in_browser_24)
            .setNegativeButton(resources.getString(R.string.no)) { dialog, which ->
                dialog.dismiss()
            }
            .setPositiveButton(resources.getString(R.string.yes)) { dialog, which ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repoList[position].htmlUrl))
                startActivity(intent)
            }
            .show()
    }

}