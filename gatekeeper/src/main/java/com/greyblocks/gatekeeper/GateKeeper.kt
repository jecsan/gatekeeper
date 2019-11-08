package com.greyblocks.gatekeeper

import android.accounts.Account
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.Log
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import kotlin.reflect.KClass


@Suppress("unused")
open class GateKeeper(context: Context, accountType: Int = R.string.account_type) {


    companion object {
        const val TAG = "GateKeeper"
    }


    private val accountManager: AccountManager = AccountManager.get(context)
    private val accountType: String = context.getString(accountType)
    private val preference = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)

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

    @Suppress("UNCHECKED_CAST")
    private fun saveList(name: String, list: List<*>, itemType: KClass<*>) {

        when (itemType) {
//            String::class -> {
//                preference.edit().putStringSet(name, (list as List<String>).toTypedArray().toSet())
//                        .apply()
//            }
//            Int::class -> {
//                val convertedList = (list as List<Int>).map {
//                    it.toString()
//                }
//                preference.edit().putStringSet(name, convertedList.toTypedArray().toSet())
//                        .apply()
//            }
//            Float::class -> {
//                val convertedList = (list as List<Float>).map {
//                    it.toString()
//                }
//                preference.edit().putStringSet(name, convertedList.toTypedArray().toSet())
//                        .apply()
//
//            }
            Long::class-> {
                val convertedList = (list as List<Long>).map {
                    it.toString()
                }
                preference.edit().putStringSet(name, convertedList.toTypedArray().toSet())
                        .apply()
            }

        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun getList(name: String, itemType: Type): List<Any>? {
        val stringSet = preference.getStringSet(name, null)

        return when (itemType::class.java) {
            String::class.java -> {
                stringSet?.toList()
            }
            Int::class.java -> {
                stringSet?.map { it.toInt() }?.toList()

            }
            Float::class.java -> {
                stringSet?.map { it.toFloat() }?.toList()
            }
            Long::class.java-> {
                stringSet?.map { it.toLong() }?.toList()
            }
            else -> null

        }
    }


    fun saveAccount(account: Any) {

        require(account::class.java.isAnnotationPresent(UserAccount::class.java)) { "Class is not recognized as @UserAccount" }

        account.javaClass.declaredFields.forEach {
            it.isAccessible = true
            val name = it.name
            val type: Class<*> = it.type
            val value = (it.get(account))

            when (type) {
                String::class.java -> preference.edit().putString(name, value as String).apply()
                Long::class.java -> preference.edit().putLong(name, value as Long).apply()
                Int::class.java -> preference.edit().putInt(name, value as Int).apply()
                Boolean::class.java -> preference.edit().putBoolean(name, value as Boolean).apply()
                Float::class.java -> preference.edit().putFloat(name, value as Float).apply()
                List::class.java -> {
                    val itemType = (it.genericType as ParameterizedType).actualTypeArguments[0]
                    saveList(name, value as List<*>, itemType as KClass<*>)
                    Log.i(TAG, "List item type: $itemType")
                }
                else -> Log.w(TAG, "Cannot save value for ${it.name}")


            }
        }
    }

    fun <T> getAccount(accountClass: Class<T>): T {

        require(accountClass.isAnnotationPresent(UserAccount::class.java)) { "Class is not recognized as @UserAccount" }

        val instance = accountClass.newInstance()
        accountClass.declaredFields.forEach {
            it.isAccessible = true
            val name = it.name

            when (it.type) {
                String::class.java -> {
                    val value = preference.getString(it.name, null)
                    it.set(instance, value)
                }

                List::class.java->{
                    val itemType = (it.genericType as ParameterizedType).actualTypeArguments[0]
                    getList(name, itemType)
                }

                else -> {
                    Log.w(TAG, "Cannot get value for ${it.name}")
                }
            }
        }

        return instance
    }


}