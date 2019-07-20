package sola.martin.kotlinmessenger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_login.*

val  TAG = "LoginActivity"
class LoginActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button_login.setOnClickListener {
            val email = email_editText_login.text.toString()
            val password = password_editText_login.text.toString()

            Log.d(TAG, "Attempt login with email/pw: $email/***")
        }

        back_to_register_textView_login.setOnClickListener {
            finish()
        }

    }
}