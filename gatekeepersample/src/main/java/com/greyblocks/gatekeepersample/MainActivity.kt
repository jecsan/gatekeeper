package com.greyblocks.gatekeepersample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.greyblocks.gatekeeper.GateKeeper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var gateKeeper: GateKeeper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gateKeeper = GateKeeper(application)

        userTv.text = "Logged in:\n\n AuthToken: ${gateKeeper.getAuthToken()}"

        logoutBtn.setOnClickListener {
            gateKeeper.logout()
            gateKeeper.requireLogin(this)

        }

        val userAccount = MyAccount()
        userAccount.id = 4
        userAccount.name = "John"
        userAccount.ids = arrayListOf(2L, 5L)


        gateKeeper.saveAccount(userAccount)

        val account:MyAccount = gateKeeper.getAccount()

        Log.d("Joed", "Saved account: $account")

    }

    override fun onResume() {
        super.onResume()



        gateKeeper.requireLogin(this)


    }


}
