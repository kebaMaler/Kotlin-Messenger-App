package sola.martin.kotlinmessenger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        register_button_register.setOnClickListener {
            val email = email_editText_register.text.toString()
            val password = password_editText_register.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText( this, "Please enter text in email/pw", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Log.d(TAG, "User e-mail is : $email")
            Log.d(TAG, "User password is : $password")

            // Firebase Authentication to create a user with email and password
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if(!it.isSuccessful) {
                        Log.w(TAG, "createUserWithEmail:failure", it.exception)
                        return@addOnCompleteListener
                    }

                    //else if successful
                    Log.d(TAG,"Successfully created user with uid: ${it.result?.user?.uid}")
                }


//            auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "createUserWithEmail:success")
//                        val user = auth.currentUser
//                        updateUI(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
//                    }
//
//                    // ...
//                }

        }

        already_have_account_register.setOnClickListener {
            Log.d(TAG, "Try to show login activity")

            // lunch the login activity somehow
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
