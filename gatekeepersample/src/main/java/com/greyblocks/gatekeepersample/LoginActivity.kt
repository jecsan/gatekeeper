package com.greyblocks.gatekeepersample

import android.accounts.AccountManager
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.greyblocks.gatekeeper.GateKeeper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            if(userNameInput.getText().isNotEmpty() && passwordInput.getText().isNotEmpty()){
                val gateKeeper = GateKeeper(application)

                //Let's assume we sent the credentials to the API and it returned 200
                loginBtn.isEnabled = false
                //Note: Password is nullable, and it is advisable not to save any password
                //If you really need to save it, atleast encrypt before saving
                Handler(Looper.getMainLooper()).postDelayed({
                    gateKeeper.enter(userNameInput.getText(),
                            "someauthtoken_skdfksdgfjsd")

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                },1500)
            }
            else{
                Snackbar.make(parentView, "Invalid username or password", Snackbar.LENGTH_LONG).show()
            }

        }
    }
}