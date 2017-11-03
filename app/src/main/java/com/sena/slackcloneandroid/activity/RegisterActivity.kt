package com.sena.slackcloneandroid.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.sena.slackcloneandroid.R
import com.sena.slackcloneandroid.api.ApiClient
import com.sena.slackcloneandroid.api.endpoint.UserInterface
import com.sena.slackcloneandroid.model.Data
import com.sena.slackcloneandroid.model.Json
import com.sena.slackcloneandroid.model.User
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterActivity : AppCompatActivity() {

    var loading: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        buttonRegister.setOnClickListener {
            register()
        }
    }

    private fun register() {
        if (!validateRegister()) return
        showLoading()

        val newUser = User(textUsername.text.toString(), textEmail.text.toString(),
                textPassword.text.toString(), "")

        val call = ApiClient.getClient()!!.create(UserInterface::class.java)
                .post(Json(Data("users", newUser)))

        call.enqueue(object : Callback<Json<User>> {
            override fun onResponse(call: Call<Json<User>>, response: Response<Json<User>>) {
                loading!!.dismiss()
                if (response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Register successful", Toast.LENGTH_LONG)
                } else {
                    Toast.makeText(this@RegisterActivity, "Error in the request", Toast.LENGTH_LONG)
                }
            }

            override fun onFailure(call: Call<Json<User>>, t: Throwable) {
                loading!!.dismiss()
                t.printStackTrace()
            }
        })
    }

    private fun validateRegister(): Boolean {
        var isValid = true
        val fields = arrayOf(textUsername, textEmail, textPassword, textPasswordRepeat)
        fields.forEach { field ->
            if ("" == field.text.toString()) {
                isValid = false
                field.error = "This Field is required"
            }
        }

        if (textPassword.text.toString() != textPasswordRepeat.text.toString()) {
            isValid = false
            textPasswordRepeat.error = "The passwords are different"
        }

        return isValid
    }

    private fun showLoading() {
        loading = ProgressDialog.show(this, "Loading",
                "Loading. Please wait...", true)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            val intent = Intent(context, RegisterActivity::class.java)
            return intent
        }
    }
}
