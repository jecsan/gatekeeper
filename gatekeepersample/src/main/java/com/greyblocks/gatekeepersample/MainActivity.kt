package com.greyblocks.gatekeepersample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.codecodecoffee.gatekeeper.GateKeeper
import com.codecodecoffee.gatekeeper.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var gateKeeper: GateKeeper

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gateKeeper = GateKeeper(application)

        GlobalScope.launch {
            gateKeeper.accountStateFlow.collectLatest {
                Log.d("MainActivity", "onCreate: $it")
            }
        }

        binding.userTv.text = "Logged in:\n\n AuthToken: ${gateKeeper.getAuthToken()}"

        binding.logoutBtn.setOnClickListener {
            gateKeeper.logout()
            gateKeeper.requireLogin(this@MainActivity)
        }
    }

    override fun onResume() {
        super.onResume()

        gateKeeper.requireLogin(this@MainActivity)

    }


}
