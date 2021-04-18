package com.greyblocks.gatekeeper

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.animation.AnimationUtils
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


open class GateKeeper(context: Context, accountType: Int = R.string.account_type) {

    companion object {
        const val TAG = "GateKeeper"
        const val ACCOUNT = "gatekeeper-account"
    }

    private val accountManager: AccountManager = AccountManager.get(context)
    private val accountType: String = context.getString(accountType)

    @PublishedApi
    internal val json: Json = Json {
        isLenient = true
        encodeDefaults = true
    }

    @PublishedApi
    internal val preference = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

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
        accountManager.addAccountExplicitly(Account(user, accountType), null, null)
        accountManager.setAuthToken(
            getCurrentAccount(),
            AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS,
            authToken
        )
    }

    fun refresh(authToken: String) {
        if (getCurrentAccount() == null) throw (IllegalStateException("User is not logged in"))
        accountManager.setAuthToken(
            getCurrentAccount(),
            AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS,
            authToken
        )
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
            preference.edit().clear().apply()
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    fun isLoggedIn(): Boolean {
        return getAuthToken() != null
    }

    /**
     * Check if logged in, if not, close the current activity and start the Gate
     */
    fun requireLogin(activity: Activity) {
        if (!isLoggedIn()) {
            accountManager.addAccount(
                accountType, AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS, null, null, activity,
                null, null
            )
            activity.finishAffinity()
        }
    }


    inline fun <reified T>getAccount(): T{
        if(!isLoggedIn()) throw IllegalStateException("You are not logged in")
        return json.decodeFromString(preference.getString(ACCOUNT, null) ?: "")
    }

    inline fun <reified T>saveAccount(account: T) {
        require(T::class.java.isAnnotationPresent(UserAccount::class.java)) { "Class is not recognized as @UserAccount" }

        val jsonString = json.encodeToString(account)
        preference.edit().putString(ACCOUNT, jsonString).apply()
    }





}