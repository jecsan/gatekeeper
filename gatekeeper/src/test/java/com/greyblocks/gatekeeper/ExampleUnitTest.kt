package com.greyblocks.gatekeeper

import android.accounts.Account
import android.accounts.AccountManager
import android.content.SharedPreferences
import android.os.Handler
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.stubbing.Answer
import org.mockito.verification.VerificationMode
import java.util.*



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
open class ExampleUnitTest {

    @Mock
    lateinit var accountManager: AccountManager
    @Mock
    lateinit var sharedPreferences: SharedPreferences

    val accountType = "test_account"
    val authToken = "abcd"
    val existingAccountName = "joed1"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(accountManager.getAccountsByType(accountType)).thenReturn(arrayOf(Account(existingAccountName, accountType)))
    }


    @Test
    fun testLoginCheckExistingAccount() {

        val gateKeeper = GateKeeper(accountManager, sharedPreferences, accountType, Handler())
        gateKeeper.login(Account("joed", accountType), null, authToken, null)
        Mockito.verify(accountManager, Mockito.atLeastOnce()).getAccountsByType(accountType)
    }

    @Test
    fun testLogoutIfExisting() {

//        Mockito.`when`(accountManager.peekAuthToken(Account(existingAccountName,accountType),AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS)).thenReturn(authToken)
//
//        val gateKeeper = GateKeeper(accountManager,sharedPreferences,accountType)
//
//        gateKeeper.login(Account("joed", accountType), null, authToken, null)
//        Mockito.verify(accountManager,Mockito.atLeastOnce()).invalidateAuthToken(accountType,authToken)
    }
}
