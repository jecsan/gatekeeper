package com.greyblocks.gatekeepersample

import android.app.Application
import com.codecodecoffee.gatekeeper.Gate

class SampleApp : Application(), Gate {
    override fun getGateClass(): Class<*> {
        return LoginActivity::class.java
    }


}