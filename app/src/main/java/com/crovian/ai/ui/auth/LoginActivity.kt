package com.crovian.ai.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.crovian.ai.MainActivity
import com.crovian.ai.data.model.User
import com.crovian.ai.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInStatusCodes
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.crovian.ai.R

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    private val auth = FirebaseAuth.getInstance()

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if already logged in
        if (auth.currentUser != null) {
            goToDashboard()
            return
        }

        setupGoogleSignIn()
        binding.btnGoogleSignIn.setOnClickListener { signIn() }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .requestProfile()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        binding.progressBar.visibility = View.VISIBLE
        binding.btnGoogleSignIn.isEnabled = false
        startActivityForResult(googleSignInClient.signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                binding.progressBar.visibility = View.GONE
                binding.btnGoogleSignIn.isEnabled = true

                val statusCode = e.statusCode
                val errorMessage = GoogleSignInStatusCodes.getStatusCodeString(statusCode)
                Toast.makeText(this, "Sign-in failed ($statusCode): $errorMessage", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserToFirestore(auth.currentUser!!)
                } else {
                    binding.progressBar.visibility = View.GONE
                    binding.btnGoogleSignIn.isEnabled = true
                    Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveUserToFirestore(firebaseUser: FirebaseUser) {
        val db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(firebaseUser.uid)

        userRef.get().addOnSuccessListener { document ->
            if (!document.exists()) {
                // New user — create record
                val user = User(
                    uid = firebaseUser.uid,
                    name = firebaseUser.displayName ?: "",
                    email = firebaseUser.email ?: "",
                    photoUrl = firebaseUser.photoUrl?.toString() ?: "",
                    dailyWage = 0.0,
                    language = "en"
                )
                userRef.set(user)
                    .addOnSuccessListener { goToDashboard() }
                    .addOnFailureListener { goToDashboard() }
            } else {
                goToDashboard()
            }
        }.addOnFailureListener {
            goToDashboard()
        }
    }

    private fun goToDashboard() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
