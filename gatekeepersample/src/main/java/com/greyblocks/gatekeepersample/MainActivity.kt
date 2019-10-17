package com.greyblocks.gatekeepersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.greyblocks.gatekeeper.GateKeeper
import com.greyblocks.gatekeeper.UserAccountConverter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var gateKeeper: GateKeeper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gateKeeper = GateKeeper(application)

        userTv.text = "Logged in as ${gateKeeper.getCurrentAccount()?.name}\n\n AuthToken: ${gateKeeper.getAuthToken()}"

        logoutBtn.setOnClickListener {
            gateKeeper.logout()
            gateKeeper.requireLogin(this)
            gateKeeper.saveUserAccount(1)
           val fg:String =  gateKeeper.getUserAccount()

            val acc:String = gateKeeper.getUserAccount()

            object: UserAccountConverter<Int,String>{
                override fun getUserAccount(gateKeeper: GateKeeper): String {
                    TODO()
                }

                override fun saveUserAccount(userAccount: Int, gateKeeper: GateKeeper) {
                }

            }
        }
    }

    override fun onResume() {
        super.onResume()



        gateKeeper.requireLogin(this)

    }


}
