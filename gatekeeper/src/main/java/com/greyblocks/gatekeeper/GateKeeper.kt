package com.greyblocks.gatekeeper

import android.accounts.Account
import android.accounts.AccountManager
import android.accounts.AccountManagerFuture
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import java.util.concurrent.TimeUnit


open class GateKeeper(context: Context) {

    companion object {
        const val TAG = "GateKeeper"
    }

    private val accountManager: AccountManager = AccountManager.get(context)
    private val accountType: String = context.getString(R.string.account_type)

    private fun getCurrentAccount(): Account? {
        val accounts = accountManager.getAccountsByType(accountType)
        if (accounts.isNotEmpty()) {
            return accounts[0]
        }
        return null
    }


    fun getAuthToken(): String? {
        val account = getCurrentAccount()
        return account?.let {
            accountManager.peekAuthToken(
                getCurrentAccount(),
                AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS
            )
        }
    }


    fun enter(user: String, authToken: String) {
        if (getCurrentAccount() != null) {
            logout()
        }
        val result = accountManager.addAccountExplicitly(
            Account(user, accountType),
            null, null
        )
        if (result) {

            accountManager.setAuthToken(
                getCurrentAccount(),
                AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS,
                authToken
            )
        } else {
            logout()
        }

    }

    fun refresh(authToken: String) {
        if (getCurrentAccount() == null) throw (IllegalStateException("User is not logged in"))
        accountManager.setAuthToken(
            getCurrentAccount(),
            AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS,
            authToken
        )
    }

    /**
     * Remove account from account manager and invalidate auth token
     */
    fun logout() {

        if (getCurrentAccount() != null) {
            accountManager.invalidateAuthToken(accountType, getAuthToken())
            accountManager.removeAccountExplicitly(getCurrentAccount())
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    /**
     * Check if logged in, if not, close the current activity and start the Gate
     */
    fun requireLogin(activity: Activity?): AccountManagerFuture<Bundle>? {
        if (!isLoggedIn()) {

            val result = accountManager.addAccount(
                accountType, AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS,
                null, null, activity,
                null, null
            )

            if (activity == null) return result
        }
        return null
    }


}