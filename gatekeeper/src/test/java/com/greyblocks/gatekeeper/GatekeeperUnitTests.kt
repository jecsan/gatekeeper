package com.greyblocks.gatekeeper

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
open class GatekeeperUnitTests {
//
//    @Mock
//    lateinit var accountManager: AccountManager
//
//
//    val accountType = "test_account"
//    val authToken = "abcd"
//    val existingAccountName = "joed1"
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        Mockito.`when`(accountManager.getAccountsByType(accountType)).thenReturn(arrayOf(Account(existingAccountName, accountType)))
//    }
//
//
//    @Test
//    fun testLoginCheckExistingAccount() {
//
//        val gateKeeper = GateKeeper(accountManager, accountType)
//        gateKeeper.enter("Joed", accountType, authToken, Bundle())
//        Mockito.verify(accountManager, Mockito.atLeastOnce()).getAccountsByType(accountType)
//    }

    @Test
    fun testLogoutIfExisting() {

//        Mockito.`when`(accountManager.peekAuthToken(Account(existingAccountName,accountType),AccountAuthenticator.AUTHTOKEN_TYPE_FULL_ACCESS)).thenReturn(authToken)
//
//        val gateKeeper = GateKeeper(accountManager,accountType)
//
//        gateKeeper.enter("Joed", accountType, authToken, Bundle())
//        Mockito.verify(accountManager,Mockito.atLeastOnce()).invalidateAuthToken(accountType,authToken)
    }
}
