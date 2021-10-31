package com.greyblocks.gatekeeper

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils

class AccountAuthenticator internal constructor(private val context: Context) :
    AbstractAccountAuthenticator(context) {

    private val loginActivityClass: Class<*> = (context as Gate).getGateClass()
    override fun editProperties(
        response: AccountAuthenticatorResponse?,
        accountType: String
    ): Bundle? {
        return null
    }

    override fun addAccount(
        response: AccountAuthenticatorResponse?,
        accountType: String?,
        authTokenType: String?,
        requiredFeatures: Array<String>?,
        options: Bundle?
    ): Bundle {
        val intent = Intent(context, loginActivityClass)

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(ARG_ACCOUNT_TYPE, accountType)
        intent.putExtra(ARG_AUTH_TYPE, authTokenType)
        intent.putExtra(ARG_IS_ADDING_NEW_ACCOUNT, true)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    override fun confirmCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        options: Bundle
    ): Bundle? {
        return null
    }

    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle
    ): Bundle {
        if (authTokenType != AUTHTOKEN_TYPE_FULL_ACCESS) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType")
            return result
        }
        val am = AccountManager.get(context)
        val authToken = am.peekAuthToken(account, authTokenType)
        if (!TextUtils.isEmpty(authToken)) {
            val result = Bundle()
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name)
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type)
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken)
            return result
        }
        val intent = Intent(context, loginActivityClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response)
        intent.putExtra(ARG_ACCOUNT_TYPE, account.type)
        intent.putExtra(ARG_AUTH_TYPE, authTokenType)
        intent.putExtra(ARG_ACCOUNT_NAME, account.name)
        val bundle = Bundle()
        bundle.putParcelable(AccountManager.KEY_INTENT, intent)
        return bundle
    }

    override fun getAuthTokenLabel(authTokenType: String): String? {
        return null
    }

    override fun updateCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle
    ): Bundle? {
        return null
    }

    override fun hasFeatures(
        response: AccountAuthenticatorResponse,
        account: Account,
        features: Array<String>
    ): Bundle {
        return Bundle()
    }

    companion object {
        private const val ARG_ACCOUNT_NAME = "arg_account_name"
        private const val ARG_ACCOUNT_TYPE = "arg_account_type"
        private const val ARG_AUTH_TYPE = "arg_auth_type"
        private const val ARG_IS_ADDING_NEW_ACCOUNT = "is_adding_new_account"
        const val AUTHTOKEN_TYPE_FULL_ACCESS = "authtoken_type_full_access"
    }

}