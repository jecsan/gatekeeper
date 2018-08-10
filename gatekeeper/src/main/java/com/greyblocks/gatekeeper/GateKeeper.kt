package com.greyblocks.gatekeeper

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.os.Build
import android.os.Bundle

@Suppress("unused")
open class GateKeeper(private val accountManager: AccountManager,
                      private val accountType: String) {


    fun getCurrentAccount(): Account? {
        val accounts = accountManager.getAccountsByType(accountType)
        if (accounts.isNotEmpty()) {
            return accounts[0]
        }
        return null
    }

    fun getAuthToken(): String? {
        val account = getCurrentAccount()
        return account?.let { accountManager.peekAuthToken(getCurrentAccount(), AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS) }
    }


    fun enter(user: String, password: String?, authToken: String, userData: Bundle? = null) {
        if (getCurrentAccount() != null) {
            logout()
        }
        accountManager.addAccountExplicitly(Account(user, accountType), password, userData)
        accountManager.setAuthToken(getCurrentAccount(), AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS, authToken)
    }

    fun saveUserData(key: String, value: Long) {
        accountManager.setUserData(getCurrentAccount(), key, value.toString())
    }

    fun saveUserData(key: String, value: Int) {
        accountManager.setUserData(getCurrentAccount(), key, value.toString())
    }

    fun saveUserData(key: String, value: String) {
        accountManager.setUserData(getCurrentAccount(), key, value)
    }

    fun getUserData(key: String, defaultData: String? = null): String? {
        return accountManager.getUserData(getCurrentAccount(), key) ?: defaultData
    }

    fun getLong(key: String): Long {
        return accountManager.getUserData(getCurrentAccount(), key).toLongOrNull() ?: 0L
    }

    fun getInt(key: String): Int {
        return accountManager.getUserData(getCurrentAccount(), key).toIntOrNull() ?: 0
    }

    fun logout() {
        if (getCurrentAccount() != null) {
            accountManager.invalidateAuthToken(accountType, getAuthToken())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                accountManager.removeAccountExplicitly(getCurrentAccount())
            } else {
                @Suppress("DEPRECATION")
                accountManager.removeAccount(getCurrentAccount(), null, null)
            }
        }

    }

    @Suppress("MemberVisibilityCanBePrivate")
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