package com.greyblocks.gatekeeper;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by dyoed on 3/22/17.
 */

public class AppAuthenticatorService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new AccountAuthenticator(this.getApplication()).getIBinder();
    }
}
