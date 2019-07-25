package sola.martin.kotlinmessenger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

val  TAG = "LoginActivity"
class LoginActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_editText_login.text.toString()
            val password = password_editText_login.text.toString()

            if (email.isEmpty() || password.isEmpty()){

                Toast.makeText(this, "Please enter text in email/pw", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            Log.d(TAG, "Attempt login with email/pw: $email/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (!it.isSuccessful){
                        Log.d(TAG,  "Login:failure", it.exception)
                        return@addOnCompleteListener
                    }

                    Log.d(TAG, "Authorisation compleate: with: ${it.result?.user?.uid}")
                    val intent = Intent(this, LatestMessagesActivity:: class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                .addOnFailureListener {
                    Log.d(TAG, "Login failure: ${it.message}")
                }

        }

        back_to_register_textView_login.setOnClickListener {
            finish()
        }

    }
}