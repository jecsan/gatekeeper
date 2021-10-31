package com.greyblocks.gatekeepersample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.greyblocks.gatekeeper.GateKeeper
import com.greyblocks.gatekeepersample.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.loginBtn.setOnClickListener {
            if (binding.userNameInput.getText().isNotEmpty() && binding.passwordInput.getText()
                    .isNotEmpty()
            ) {
                val gateKeeper = GateKeeper(application)

                //Let's assume we sent the credentials to the API and it returned 200
                binding.loginBtn.isEnabled = false
                //Note: Password is nullable, and it is advisable not to save any password
                //If you really need to save it, atleast encrypt before saving
                Handler(Looper.getMainLooper()).postDelayed({
                    gateKeeper.enter(
                        binding.userNameInput.getText(),
                        "someauthtoken_skdfksdgfjsd"
                    )

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()

                }, 1500)
            } else {
                Snackbar.make(
                    binding.parentView,
                    "Invalid username or password",
                    Snackbar.LENGTH_LONG
                ).show()
            }

        }
    }
}