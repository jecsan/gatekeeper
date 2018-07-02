package com.greyblocks.gatekeepersample

import android.accounts.AccountManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.support.v7.app.AppCompatActivity
import com.greyblocks.gatekeeper.GateKeeper
import com.greyblocks.gatekeeper.User
import com.greyblocks.gatekeeper.UserDetailMapper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var gateKeeper: GateKeeper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gateKeeper = GateKeeper(AccountManager.get(this),PreferenceManager.getDefaultSharedPreferences(this),
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

    class UserMapper: UserDetailMapper<User>{
        override fun getUser(): User {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun saveUser(user: User) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}
