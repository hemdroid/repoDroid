package com.hemanth.repodroid.ui

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.hemanth.repodroid.data.RepoDatabase
import com.hemanth.repodroid.data.RepoTable
import com.hemanth.repodroid.data.RepositoryTable
import com.hemanth.repodroid.databinding.FragmentAddBinding
import com.hemanth.repodroid.model.RepositoryData
import com.hemanth.repodroid.viewModel.UserViewModel
import com.hemanth.repodroid.viewModel.UserViewModelFactory

/**
 * A simple [Fragment] subclass.
 * Use the [AddFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFragment : Fragment() {

    private lateinit var binding : FragmentAddBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var repoDao: RepositoryTable
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repoDao = RepoDatabase.getDatabase(requireContext()).repoDao()

        val factory = UserViewModelFactory(repoDao)
        viewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]

        binding.materialAppBar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.addBtn.setOnClickListener {
            if (isEmptyTextInput(binding.ownerEditText) || isEmptyTextInput(binding.repoEditText)){
                binding.ownerEditText.editText?.error ="Enter Owner name"
                binding.repoEditText.editText?.error = "Enter Repository Name"
            }else{
                findTheRepo(binding.ownerEditText.editText?.text.toString(), binding.repoEditText.editText?.text.toString())
            }
        }
    }
    private fun findTheRepo(userName:String, repoName:String){
        viewModel.githubRepository(userName, repoName)
        viewModel.repoResponse.observe(viewLifecycleOwner) { response ->
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val repoName = response.body()!!.name
                    val descriptionOfRepo = response.body()!!.description
                    descriptionOfRepo.toString()
                    val htmlUrl = response.body()!!.html_url
                    val repo = RepoTable(repoName,  descriptionOfRepo, htmlUrl, userName)
                    viewModel.insertTheRepo(repo)
                    parentFragmentManager.popBackStack()
                } else {
                    binding.ownerEditText.editText?.text?.clear()
                    binding.repoEditText.editText?.text?.clear()
                    binding.ownerEditText.editText?.error = "Enter Correct Owner name"
                    binding.repoEditText.editText?.error = "Enter Correct Repository Name"
                }
            } else {
                binding.ownerEditText.editText?.text?.clear()
                binding.repoEditText.editText?.text?.clear()
                binding.ownerEditText.error = "Enter Correct Username/Organization"
                binding.repoEditText.error = "Enter Correct Repo Name"

            }
        }

    }

    private fun isEmptyTextInput(editText: TextInputLayout): Boolean {
        return editText.editText?.text.toString().trim().isEmpty()
    }


}