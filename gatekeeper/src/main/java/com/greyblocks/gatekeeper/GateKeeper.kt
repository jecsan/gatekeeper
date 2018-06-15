package com.greyblocks.gatekeeper

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.Handler

open class GateKeeper(private val accountManager: AccountManager, private val sharedPreferences: SharedPreferences,
                      private val accountType: String, private val handler: Handler) {

    fun getCurrentAccount(): Account? {
        val accounts = accountManager.getAccountsByType(accountType)
        if (accounts != null && accounts.isNotEmpty()) {
            return accounts[0]
        }
        return null
    }

    fun getAuthToken(): String? {
        val account = getCurrentAccount()
        return account?.let { accountManager.peekAuthToken(getCurrentAccount(), AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS) }
    }

    fun login(account: Account, password: String?, authToken: String, userData: Bundle? = null) {
        if (getCurrentAccount() != null) {
            logout()
        }
        accountManager.addAccountExplicitly(account, password, userData)
        accountManager.setAuthToken(getCurrentAccount(), AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS, authToken)
    }

    fun saveUserData(key: String, value: String) {
        accountManager.setUserData(getCurrentAccount(), key, value)
    }

    fun getUserData(key: String): String? {
        return accountManager.getUserData(getCurrentAccount(), key)
    }

    fun logout() {
        if(getCurrentAccount() != null){
            accountManager.invalidateAuthToken(accountType, getAuthToken())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                accountManager.removeAccountExplicitly(getCurrentAccount())
            } else {
                @Suppress("DEPRECATION")
                accountManager.removeAccount(getCurrentAccount(), null, null)
            }
        }

    }

    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    fun checkUserAuth(activity: Activity) {
        if (!isLoggedIn()) {
            accountManager.addAccount(accountType, AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS, null, null, activity,
                    null, null)
            activity.finish()
        }
    }


}