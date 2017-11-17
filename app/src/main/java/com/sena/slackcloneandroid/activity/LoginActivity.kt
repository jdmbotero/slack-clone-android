package com.sena.slackcloneandroid.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.sena.slackcloneandroid.App
import com.sena.slackcloneandroid.R
import com.sena.slackcloneandroid.api.ApiClient
import com.sena.slackcloneandroid.api.endpoint.AuthInterface
import com.sena.slackcloneandroid.model.User
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    var loading: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        buttonLogin.setOnClickListener {
            login()
        }

        buttonRegister.setOnClickListener {
            startActivity(RegisterActivity.newIntent(this))
        }
    }

    private fun login() {
        if (!validateLogin()) return
        showLoading()

        val user = User(textUsername.text.toString(), textPassword.text.toString())
        val call = ApiClient.getClient()!!.create(AuthInterface::class.java)
                .login(user)

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                goneLoading()
                if (response.isSuccessful) {
                    val user = response.body()
                    (application as App).preferences!!.setUser(user)
                    Toast.makeText(this@LoginActivity, "login successful", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Error in the request", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                goneLoading()
                t.printStackTrace()
            }
        })

    }

    private fun validateLogin(): Boolean {
        var isValid = true
        val fields = arrayOf(textUsername, textPassword)
        fields.forEach { field ->
            if ("" == field.text.toString()) {
                isValid = false
                field.error = "This Field is required"
            }
        }

        return isValid
    }

    private fun showLoading() {
        loading = ProgressDialog.show(this, "Loading",
                "Loading. Please wait...", true)
    }

    private fun goneLoading() {
        if (null != loading) {
            runOnUiThread({
                loading!!.dismiss()
            })
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }
}
