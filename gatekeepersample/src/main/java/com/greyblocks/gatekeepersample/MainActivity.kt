package com.greyblocks.gatekeepersample

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.greyblocks.gatekeeper.GateKeeper
import com.greyblocks.gatekeepersample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var gateKeeper: GateKeeper
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gateKeeper = GateKeeper(application)

        binding.userTv.text = "Logged in:\n\n AuthToken: ${gateKeeper.getAuthToken()}"

        binding.logoutBtn.setOnClickListener {
            gateKeeper.logout()



        }
    }

    override fun onResume() {
        super.onResume()

        gateKeeper.requireLogin(this@MainActivity)

    }


}
