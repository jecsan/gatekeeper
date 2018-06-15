package com.greyblocks.gatekeeper;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by dyoed on 3/22/17.
 */

public class AppAuthenticatorService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new AccountAuthenticator(this.getApplication()).getIBinder();
    }
}
