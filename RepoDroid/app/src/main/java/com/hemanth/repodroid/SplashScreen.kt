package com.hemanth.repodroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        window.statusBarColor = getColor(R.color.primary_swift)

        Handler(Looper.getMainLooper()).postDelayed({
            val mIntent = Intent(this, MainActivity::class.java)
            startActivity(mIntent)
            finish()
        }, 2000)
    }
}