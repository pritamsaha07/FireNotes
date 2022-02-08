package com.example.firenotes

import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.okhttp.Dispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

private const val TAG = "SigninActivity"
private lateinit var auth:FirebaseAuth

class SigninActivity : AppCompatActivity() {
  private lateinit var signInButton: SignInButton
  private lateinit var  googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        signInButton=findViewById(R.id.signin)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth= Firebase.auth
        signInButton.setOnClickListener{
            signin()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentuser=auth.currentUser
        updateUI(currentuser)
    }

    private fun signin(){
        val signIntent= googleSignInClient.signInIntent
        getresult.launch(signIntent)
    }
    private val getresult=registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
           if(it.resultCode== RESULT_OK){
               val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
               try {
                   // Google Sign In was successful, authenticate with Firebase
                   val account = task.getResult(ApiException::class.java)!!
                   Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                   firebaseAuthWithGoogle(account.idToken!!)
               } catch (e: ApiException) {
                   // Google Sign In failed, update UI appropriately
                   Log.w(TAG, "Google sign in failed", e)
               }
           }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential=GoogleAuthProvider.getCredential(idToken,null)
             GlobalScope.launch(Dispatchers.IO) {
               val auth=auth.signInWithCredential(credential).await()
                 val firebaseUser=auth.user

                 withContext(Dispatchers.Main){
                     updateUI(firebaseUser)
                 }
             }



    }
    private fun updateUI(firebaseUser: FirebaseUser?){
        if(firebaseUser!=null){
            val intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}