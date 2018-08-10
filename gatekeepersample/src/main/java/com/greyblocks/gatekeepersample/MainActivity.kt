package com.greyblocks.gatekeepersample

import android.accounts.AccountManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.greyblocks.gatekeeper.GateKeeper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var gateKeeper: GateKeeper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gateKeeper = GateKeeper(AccountManager.get(this),
                getString(R.string.account_type))

        userTv.text = "Logged in as ${gateKeeper.getCurrentAccount()?.name}\n\n AuthToken: ${gateKeeper.getAuthToken()}"

        logoutBtn.setOnClickListener {
            gateKeeper.logout()
            gateKeeper.checkUserAuth(this)
        }
    }

    override fun onResume() {
        super.onResume()

        gateKeeper.checkUserAuth(this)

    }

    class MyUser(var name:String, var phone: String){

    }

}
