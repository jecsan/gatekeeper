package com.greyblocks.gatekeeper

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.os.Build


@Suppress("unused")
open class GateKeeper(context: Context, accountType: Int = R.string.account_type) {

    var userConverter: UserAccountConverter<Any, Any>? = null
    private val accountManager: AccountManager = AccountManager.get(context)
    private val accountType: String = context.getString(accountType)
    private val preference = context.getSharedPreferences("gatekeeper", Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getUserAccount() = userConverter?.getUserAccount(this) as T

    fun <T : Any> saveUserAccount(account: T) {
        userConverter?.saveUserAccount(account, this)
    }

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


    fun enter(user: String, password: String?, authToken: String) {
        if (getCurrentAccount() != null) {
            logout()
        }
        accountManager.addAccountExplicitly(Account(user, accountType), password, null)
        accountManager.setAuthToken(getCurrentAccount(), AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS, authToken)
    }


    /**
     * Key-Value pair
     */
    fun saveStringData(vararg keyValue: Pair<String, String>) {
        val editor = preference.edit()
        keyValue.forEach {
            editor.putString(it.first, it.second)
        }
        editor.apply()
    }

    /**
     * Key-Value pair
     */
    fun saveIntData(vararg keyValue: Pair<String, Int>) {
        val editor = preference.edit()
        keyValue.forEach {
            editor.putInt(it.first, it.second)
        }
        editor.apply()
    }

    /**
     * Key-Value pair
     */
    fun saveLongData(vararg keyValue: Pair<String, Long>) {
        val editor = preference.edit()
        keyValue.forEach {
            editor.putLong(it.first, it.second)
        }
        editor.apply()
    }

    fun saveList(key: String, vararg values: Int) {
        val editor = preference.edit()
        editor.putStringSet(key, values.map { it.toString() }.toSet())
        editor.apply()
    }

    fun saveList(key: String, vararg values: Long) {
        val editor = preference.edit()
        editor.putStringSet(key, values.map { it.toString() }.toSet())
        editor.apply()
    }

    fun saveList(key: String, vararg values: Float) {
        val editor = preference.edit()
        editor.putStringSet(key, values.map { it.toString() }.toSet())
        editor.apply()
    }

    fun saveList(key: String, vararg values: String) {
        val editor = preference.edit()
        editor.putStringSet(key, values.toSet())
        editor.apply()
    }

    fun saveUserData(key: String, value: Boolean) {
        preference.edit().putBoolean(key, value).apply()
    }

    fun saveUserData(key: String, value: Int) {
        preference.edit().putInt(key, value).apply()
    }

    fun saveUserData(key: String, value: String) {
        preference.edit().putString(key, value).apply()
    }

    fun getUserData(key: String, defaultData: String? = null): String? {
        return preference.getString(key, defaultData)
    }

    fun getLong(key: String): Long {
        return preference.getLong(key, 0)
    }

    fun getInt(key: String): Int {
        return preference.getInt(key, 0)
    }

    fun getBoolean(key: String): Boolean {
        return preference.getBoolean(key, false)
    }

    fun getStringList(key: String): List<String>? {
        return preference.getStringSet(key, null)?.toList()
    }

    fun getIntList(key: String): List<Int>? {
        return preference.getStringSet(key, null)?.map {
            it.toInt()
        }?.toList()
    }

    fun getLongList(key: String): List<Long>? {
        return preference.getStringSet(key, null)?.map {
            it.toLong()
        }?.toList()
    }

    fun getFloatList(key: String): List<Float>? {
        return preference.getStringSet(key, null)?.map {
            it.toFloat()
        }?.toList()
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
            accountManager.addAccount(accountType, AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS, null, null, activity,
                    null, null)
            activity.finishAffinity()
        }
    }


}