package com.greyblocks.gatekeepersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.greyblocks.gatekeeper.GateKeeper
import com.greyblocks.gatekeepersample.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var gateKeeper: GateKeeper
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
