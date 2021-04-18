package com.greyblocks.gatekeeper

import android.os.Build
import androidx.test.core.app.ApplicationProvider
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.LOLLIPOP])
class GatekeeperUnitTests {

    private lateinit var gateKeeper: GateKeeper

    @Before
    fun setup() {
        gateKeeper = GateKeeper(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown() {
        gateKeeper.logout()
    }

    @Test
    fun test_saveListSuccess() {
        val userAccount = MyAccount()
        userAccount.id = 4
        userAccount.name = "John"
        userAccount.ids = arrayListOf(2L, 5L)
        userAccount.ints = arrayListOf(22, 33)
        userAccount.bools = arrayListOf(false, true)
        userAccount.floats = arrayListOf(10.2f, 2.2f)
        userAccount.strings = arrayListOf("hmm", "hmm2")
        gateKeeper.enter("test", "tokenzzz")

        gateKeeper.saveAccount(userAccount)
        val account: MyAccount = gateKeeper.getAccount() as MyAccount

        assert(account.ids == userAccount.ids)
        assert(account.floats == userAccount.floats)
        assert(account.strings == userAccount.strings)
        assert(account.ints == userAccount.ints)

    }

    @Test
    fun test_saveWithoutAnnotations_fail() {
        val userAccount = MyAccount2()
        userAccount.id = 4
        userAccount.name = "John"
        userAccount.ids = arrayListOf(2L, 5L)
        userAccount.ints = arrayListOf(22, 33)
        userAccount.bools = arrayListOf(false, true)
        userAccount.floats = arrayListOf(10.2f, 2.2f)
        userAccount.strings = arrayListOf("hmm", "hmm2")

        val result = kotlin.runCatching { gateKeeper.saveAccount(userAccount) }
        assert(result.isFailure && result.exceptionOrNull() is IllegalArgumentException)

    }


    @Test
    fun test_saveAccount_success() {
        val userAccount = MyAccount()
        userAccount.id = 4
        userAccount.name = "John"
        gateKeeper.enter("test", "tokenzzz")

        gateKeeper.saveAccount(userAccount)
        val account: MyAccount = gateKeeper.getAccount() as MyAccount

        assert(userAccount.name == account.name)
        assert(userAccount.id == account.id)
    }

    @Test
    fun test_saveAccount_NoDefaultConstructor_success() {
        val userAccount = MyAccount4("John", 4)
        gateKeeper.enter("test", "tokenzzz")

        gateKeeper.saveAccount(userAccount)
        val account: MyAccount4 = gateKeeper.getAccount()

        assert(userAccount.name == account.name)
        assert(userAccount.id == account.id)
    }


    @Test
    fun test_getUserWhenLoggedOut_throwError() {
        gateKeeper.enter("test", "tokenzzz")
        val userAccount = MyAccount()
        userAccount.id = 4
        userAccount.name = "John"

        gateKeeper.saveAccount(userAccount)
        gateKeeper.logout()

        val result = kotlin.runCatching {
            val account: MyAccount = gateKeeper.getAccount()
        }
        assert(result.isFailure)

    }

    @Test
    fun test_displayProperStatus() {

        assert(!gateKeeper.isLoggedIn())

        gateKeeper.enter("test", "tokenzzz")
        val userAccount = MyAccount()
        userAccount.id = 4
        userAccount.name = "John"

        assert(gateKeeper.isLoggedIn())

        gateKeeper.saveAccount(userAccount)
        gateKeeper.logout()

        assert(!gateKeeper.isLoggedIn())

    }

    @Test
    fun `test refresh token`() {

        gateKeeper.enter("test", "tokenzzz")

        assert(gateKeeper.isLoggedIn())

        gateKeeper.refresh("tokenzzzzzz")

        assert(gateKeeper.getAuthToken() == "tokenzzzzzz")
        assert(gateKeeper.isLoggedIn())

    }


    @Test
    fun test_crashGetAccountWhenLoggedOut() {
        gateKeeper.enter("test", "tokenzzz")
        val userAccount = MyAccount()
        userAccount.id = 4
        userAccount.name = "John"
        gateKeeper.saveAccount(userAccount)
        gateKeeper.logout()

        try {
            val nulLAccount: MyAccount = gateKeeper.getAccount()

        } catch (e: IllegalStateException) {
            assert(true)

        }

//        val account = gateKeeper.getCurrentAccount()
    }
}
