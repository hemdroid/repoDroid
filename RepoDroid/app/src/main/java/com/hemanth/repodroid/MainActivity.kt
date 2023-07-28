package com.hemanth.repodroid

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.hemanth.repodroid.custom.NetworkMonitor
import com.hemanth.repodroid.viewModel.UserViewModel
import com.hemanth.repodroid.viewModel.UserViewModelFactory
import kotlinx.coroutines.isActive

class MainActivity : AppCompatActivity() {

    private lateinit var connecction :NetworkMonitor
    private lateinit var networkDialog : Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = getColor(R.color.primary_swift)

        connecction = NetworkMonitor(application)
        networkDialog = Dialog(this)

        connecction.observe(this) { isNetWorkConnected ->
            if (!isNetWorkConnected) showDialog()
            else closeDialog()
        }
    }

    private fun showDialog() {
        val connectivityMonitor = NetworkMonitor(this)
        networkDialog.setContentView(R.layout.network_layout)
        networkDialog.setCancelable(false)
        networkDialog.findViewById<Button>(R.id.retryBtn).setOnClickListener {
            connectivityMonitor.observe(this) { isNetWorkConnected ->
                if (!isNetWorkConnected) showDialog()
                else closeDialog()
            }
        }
        networkDialog.setCanceledOnTouchOutside(false)
        networkDialog.show()
    }

    private fun closeDialog(){
        networkDialog.dismiss()
    }
}