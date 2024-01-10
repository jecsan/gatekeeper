package com.codecodecoffee.gatekeeper

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Created by dyoed on 3/22/17.
 */
class AppAuthenticatorService : Service() {
    override fun onBind(intent: Intent): IBinder {
        return AccountAuthenticator(this.application).iBinder
    }
}