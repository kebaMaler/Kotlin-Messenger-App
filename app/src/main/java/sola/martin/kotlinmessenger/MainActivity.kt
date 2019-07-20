package sola.martin.kotlinmessenger

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        register_button_register.setOnClickListener {
            val email = email_editText_register.text.toString()
            val password = password_editText_register.text.toString()

            Log.d(TAG, "User e-mail is : $email")
            Log.d(TAG, "User password is : $password")
            // Firebase Authentication to create a user with email and password
        }

        already_have_account_register.setOnClickListener {
            Log.d(TAG, "Try to show login activity")

            // lunch the login activity somehow
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
