package com.example.whatfood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.ErrorCodes
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_LONG

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signInLauncher.launch(signInIntent)
    }

    // See: https://developer.android.com/training/basics/intents/result
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    // Choose authentication providers
    private val providers = arrayListOf(
        AuthUI.IdpConfig.EmailBuilder().build(),
        AuthUI.IdpConfig.GoogleBuilder().build()
    )

    // Create and launch sign-in intent
    private val signInIntent = AuthUI.getInstance()
        .createSignInIntentBuilder()
        .setAvailableProviders(providers)
        .setLogo(R.drawable.what_food_logo)
        .setTheme(R.style.ThemeOverlay_AppCompat_Dark)
        .build()

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            //val user = FirebaseAuth.getInstance().currentUser
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            when {
                response == null -> {
                    finish()
                }
                response.error?.errorCode == ErrorCodes.NO_NETWORK -> {

                    val snack = Snackbar.make(
                        this,
                        View(LoginActivity()),
                        "An error occurred, are you connected to Internet rn?",
                        LENGTH_LONG
                    )
                    snack.show()

                }
                response.error?.errorCode == ErrorCodes.UNKNOWN_ERROR -> {

                    val snack = Snackbar.make(
                        this,
                        View(LoginActivity()),
                        response.error?.errorCode.toString(),
                        LENGTH_LONG
                    )
                    snack.show()

                }
            }
        }
    }
}