package com.greyblocks.gatekeepersample

import android.app.Application
import com.greyblocks.gatekeeper.Gate

class SampleApp : Application(), Gate {
    override fun getGateClass(): Class<*> {
        return LoginActivity::class.java
    }


}