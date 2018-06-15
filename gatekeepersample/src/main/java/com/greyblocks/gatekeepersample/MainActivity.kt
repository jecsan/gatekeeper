package com.greyblocks.gatekeepersample

import android.accounts.AccountManager
import android.accounts.AccountManagerCallback
import android.accounts.AccountManagerFuture
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.preference.PreferenceManager
import android.util.Log
import com.greyblocks.gatekeeper.AccountAuthenticator
import com.greyblocks.gatekeeper.GateKeeper

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onResume() {
        super.onResume()
        val actmngr = AccountManager.get(this)
//        actmngr.addAccount(getString(R.string.account_type),AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS,null,null,
//                LoginActivity(),{},Handler(Looper.getMainLooper()))
        val gateKeeper = GateKeeper(AccountManager.get(this),PreferenceManager.getDefaultSharedPreferences(this),
                getString(R.string.account_type), Handler(Looper.getMainLooper()))
        actmngr.getAuthTokenByFeatures(getString(R.string.account_type), AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS,
                null, this, null, null,
                { accountManagerFuture ->
                },null)
        Log.d("Joed","tae$gateKeeper")
    }
}
