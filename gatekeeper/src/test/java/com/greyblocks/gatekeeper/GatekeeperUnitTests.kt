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
@Config(sdk = [Build.VERSION_CODES.M])
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
    fun test_displayProperStatus() {

        assert(!gateKeeper.isLoggedIn())

        gateKeeper.enter("test", "tokenzzz")

        assert(gateKeeper.isLoggedIn())

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
        gateKeeper.logout()

        try {
            gateKeeper.getAuthToken()

        } catch (e: IllegalStateException) {
            assert(true)

        }

//        val account = gateKeeper.getCurrentAccount()
    }
}
