package com.slyvii.eyeam.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import com.slyvii.eyeam.R
import com.slyvii.eyeam.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        state(false)

        auth = Firebase.auth

        supportActionBar?.hide()

        binding.apply {
            signUp.setOnClickListener {
                state(true)
                if (requirementCheck()) {
                    register(
                        username.text.toString(),
                        email.text.toString(),
                        myPassword.text.toString()
                    )
                }
            }
        }
    }

    private fun requirementCheck(): Boolean {
        var state = false
        binding.apply {
            if (username.text.isNullOrEmpty() || email.text.isNullOrEmpty() || myPassword.text.isNullOrEmpty()) {
                Toast.makeText(
                    baseContext,
                    resources.getText(R.string.fill_form),
                    Toast.LENGTH_SHORT
                ).show()
            } else if (myPassword.text.toString() != confirmPassword.text.toString()) {
                Toast.makeText(
                    baseContext,
                    resources.getText(R.string.pass_confirmation),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                state = true
            }
        }
        return state
    }

    private fun register(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = task.result.user
                    if (user != null) {
                        val profileUpdates = userProfileChangeRequest {
                            displayName = name
                        }

                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    state(false)
                                    login()
                                }
                            }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            resources.getText(R.string.error),
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        applicationContext,
                        task.exception?.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun login() {
        Toast.makeText(
            applicationContext,
            resources.getText(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun state(b: Boolean){
        if (b){
            binding.progressCircular.visibility = View.VISIBLE
            binding.signUp.isEnabled = false
        } else {
            binding.progressCircular.visibility = View.GONE
            binding.signUp.isEnabled = true
        }
    }
}